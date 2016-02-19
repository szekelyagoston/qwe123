/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.users.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.commons.codec.binary.Base64;
import szakemberkereso.entites.security.User;
import szakemberkereso.enums.SecGroup;
import szakemberkereso.exceptions.AlreadyActivatedException;
import szakemberkereso.exceptions.EmailAlreadyExistException;
import szakemberkereso.exceptions.OldPasswordMisMatchException;
import szakemberkereso.exceptions.RegistrationCodeMisMatchException;
import szakemberkereso.exceptions.UserNotFoundException;
import szakemberkereso.users.login.LoginDto;

/**
 *
 * @author gusztafszon
 */
@Stateless
public class UserDao {

    @PersistenceContext(unitName = "SzakemberkeresoPU")
    private EntityManager em;

    @Inject
    EmailSessionBean emailSessionBean;

    public void registerUser(LoginDto loginDto) throws EmailAlreadyExistException {
        User user = new User();
        if (emailAlreadyExist(loginDto.getEmail())) {
            throw new EmailAlreadyExistException("Email already in use");
        } else {
            user.setEmail(loginDto.getEmail());
        }
        user.setSecGroup(SecGroup.USER);
        user.setActive(false);
        Date now = new Date();
        user.setRegistrationTimeStamp(now);
        //String url = "http://92.43.203.193:8080/szakemberkereso/#/activate?link=";
        String url = "http://localhost:8080/szakemberkereso/#/activate?link=";

        try {
            user.setActivationString(Base64.encodeBase64String(sha256coding(loginDto.getEmail() + "." + loginDto.getPassword()).getBytes()));
            user.setPassword(sha256coding(loginDto.getPassword()));

            String subject = "Regisztráció megerősítése";
            String body = "Köszönjük regisztrációját! Kérjük az alábbi kódot használva aktiválja email címét!\n Kód: " + url + user.getActivationString();

            emailSessionBean.sendEmail(loginDto.getEmail(), subject, body);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }

        em.persist(user);

    }

    private Boolean emailAlreadyExist(String email) {
        return (em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getResultList().size()) == 1;
    }

    public Boolean getUserIsActivated(String email) throws UserNotFoundException {
        User user = null;
        try {
            user = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            throw new UserNotFoundException(email);
        }

        return user.getActive();
    }

    private String sha256coding(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(str.getBytes());
        byte byteData[] = md.digest();
        StringBuilder newPassword = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            newPassword.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return newPassword.toString();
    }
    
    public User getUserByEmail(String email){
        return em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
    }
    public String activateUser(String activationString) throws NoResultException, AlreadyActivatedException, RegistrationCodeMisMatchException, UserNotFoundException {

        User user;

        try {
            user = em.createNamedQuery("User.findByActivationString", User.class).setParameter("activationString", activationString).getSingleResult();

            if (user.getActive()) {
                throw new AlreadyActivatedException("User already activated");
            } else {
                user.setActivationString(null);
                user.setActive(true);
                em.merge(user);
            }

        } catch (NoResultException e) {
            throw new UserNotFoundException("User not found");
        }

        return user.getEmail();
    }

    public User newPassword(String email) throws UserNotFoundException {
        User user = null;
        try {
            user = getUserByEmail(email);
        } catch (NoResultException e) {
            throw new UserNotFoundException(email);
        }

        try {
            String newPassword = generateString(new Random(), "qwertzuiopasdfghjklyxcvbnm1234567890", 7);
            user.setPassword(sha256coding(newPassword));
            
            String subject="Új jelszó";
            String body = "Új jelszava: "+newPassword+" Bejelentkezés után ezt megváltoztathatja a jelszóváltoztatás menüpont alatt"; 
            
            emailSessionBean.sendEmail(email, subject, body);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }

    private static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    public User changePassword(String email, String oldPassword, String newPassword) throws OldPasswordMisMatchException, UserNotFoundException {
        User user = null;
        
        try{
            user = getUserByEmail(email);
        }catch(NoResultException ex){
            throw new UserNotFoundException("Felhasználó nem található!");
        }
        
        
        String encodedPassword = null;
        String encodedOldPassword = null;
        try {
            encodedPassword = sha256coding(newPassword);
            encodedOldPassword = sha256coding(oldPassword);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (user.getPassword().equals(encodedOldPassword)){
            user.setPassword(encodedPassword);
        }else{
            throw new OldPasswordMisMatchException("A megadott régi jelszó nem egyezik!");
        }
         return user;
        
    }

}
