/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.init;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import szakemberkereso.entites.security.User;
import szakemberkereso.entities.basedata.Office;
import szakemberkereso.entities.basedata.OpeningHours;
import szakemberkereso.entities.basedata.Specialist;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.entities.checkboxdatas.Symptom;
import szakemberkereso.enums.Day;
import szakemberkereso.enums.SecGroup;

/**
 *
 * 
 * @@author gusztafszon
 */
@Startup
@Singleton
public class Init {
    
    @PersistenceContext(unitName = "SzakemberkeresoPU")
    private EntityManager em;
    
    @PostConstruct
    public void startUp(){
        
         try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String text = "2";
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String output = bigInt.toString(16);

            
            
            User admin = new User();
            admin.setEmail("2");
            admin.setPassword(output);
            admin.setSecGroup(SecGroup.ADMIN);
            
            text = "qwe123";
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            digest = md.digest();
            bigInt = new BigInteger(1, digest);
            output = bigInt.toString(16);
            
          
            User admin2 = new User();
            admin2.setEmail("admin2@email.hu");
            admin2.setPassword(output);
            admin2.setSecGroup(SecGroup.ADMIN);
            
            //1 1
            text = "1";
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            digest = md.digest();
            bigInt = new BigInteger(1, digest);
            output = bigInt.toString(16);
            
            
            User user = new User();
            user.setEmail("1");
            user.setPassword(output);
            user.setSecGroup(SecGroup.USER);
            user.getSpecialist().setFirstName("Ágoston");
            user.getSpecialist().setLastName("Székely");
            user.getSpecialist().setPhoneNumber("0036204735381");
            user.getSpecialist().setVerified(Boolean.TRUE);
            user.getSpecialist().setFirstModificationDone(Boolean.TRUE);

            
            //user2 qwe12345
            text = "qwe12345";
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            digest = md.digest();
            bigInt = new BigInteger(1, digest);
            output = bigInt.toString(16);
            
            User user2 = new User();
            user2.setEmail("user2@email.hu");
            user2.setPassword(output);
            user2.setSecGroup(SecGroup.USER);
         
//            user.getSpecialist().getOfficeList().add(new Office("Proba utca 1"));
//            user.getSpecialist().getOfficeList().get(0).setLat(47.5158496f);
//            user.getSpecialist().getOfficeList().get(0).setLng(19.0744893f);
//            
//            OpeningHours openingHour = new OpeningHours();
//            openingHour.setOpeningDay(Day.MONDAY);
//            openingHour.setFromClock(800);
//            openingHour.setToClock(1600);
//            
//            openingHour.setOffice(user.getSpecialist().getOfficeList().get(0));
//            user.getSpecialist().getOfficeList().get(0).addOpeningHour(openingHour);
//            
//            OpeningHours openingHour2 = new OpeningHours();
//            openingHour2.setOpeningDay(Day.FRIDAY);
//            openingHour2.setFromClock(1000);
//            openingHour2.setToClock(1700);
//            
//            openingHour2.setOffice(user.getSpecialist().getOfficeList().get(0));
//            user.getSpecialist().getOfficeList().get(0).addOpeningHour(openingHour2);


            
            user.setActive(true);
            user2.setActive(true);
            admin.setActive(true);
            admin2.setActive(true);
            
            //user.getSpecialist().setVerified(Boolean.TRUE);
            //user2.getSpecialist().setVerified(Boolean.TRUE);
            admin.getSpecialist().setVerified(Boolean.TRUE);
            admin2.getSpecialist().setVerified(Boolean.TRUE);
            em.persist(user);
            em.persist(user2);
            em.persist(admin);
            em.persist(admin2);
            
            
            Problem problem = new Problem();
            problem.setStringValue("Depresszió");
            problem.setVerified(Boolean.TRUE);
            
            Problem problem1 = new Problem();
            problem1.setStringValue("Fóbiák");
            problem1.setVerified(Boolean.TRUE);
            
            Symptom symptom = new Symptom();
            symptom.setStringValue("magány");
            symptom.addConnectedProblem(problem);
            
            Symptom symptom1 = new Symptom();
            symptom1.setStringValue("szomorúság");
            symptom1.addConnectedProblem(problem);
            
            Symptom symptom2 = new Symptom();
            symptom2.setStringValue("alvászavar");
            
            Symptom symptom3 = new Symptom();
            symptom3.setStringValue("hangulatingadozás");
            symptom3.addConnectedProblem(problem);
            
            Symptom symptom4 = new Symptom();
            symptom4.setStringValue("zaklatás");
            
            Symptom symptom5 = new Symptom();
            symptom5.setStringValue("félelem");
            symptom5.addConnectedProblem(problem1);
            
            problem.addConnectedSymptom(symptom);
            problem.addConnectedSymptom(symptom1);
            problem.addConnectedSymptom(symptom3);
            
            problem1.addConnectedSymptom(symptom5);
            
            em.persist(problem);
            em.persist(problem1);
            em.persist(symptom);
            em.persist(symptom1);
            em.persist(symptom2);
            em.persist(symptom3);
            em.persist(symptom4);
            em.persist(symptom5);
            
            
            
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, ex);

        }

        

    
    }
    
    
}
