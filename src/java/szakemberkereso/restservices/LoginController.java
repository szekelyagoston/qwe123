/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.restservices;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import szakemberkereso.entites.security.User;
import szakemberkereso.enums.SecGroup;
import szakemberkereso.exceptions.AlreadyActivatedException;
import szakemberkereso.exceptions.EmailAlreadyExistException;
import szakemberkereso.exceptions.OldPasswordMisMatchException;
import szakemberkereso.exceptions.RegistrationCodeMisMatchException;
import szakemberkereso.exceptions.UserNotFoundException;
import szakemberkereso.helpers.Mapper;
import szakemberkereso.users.dao.UserDao;
import szakemberkereso.users.login.ChangePasswordDto;
import szakemberkereso.users.login.LoginDto;
import szakemberkereso.users.login.UserDetailDto;


/**
 *
 * @author gusztafszon
 */
@Stateless
@Path("user")
public class LoginController {
    
    @Inject
    UserDao userDao;

    public LoginController() {
    }
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Context final SecurityContext securityContext, @Context HttpServletRequest req, String dto) throws ServletException, IOException {
        Mapper<LoginDto> mapper = new Mapper();
        
        LoginDto loginDto = new LoginDto();
        try{
            loginDto = mapper.get(dto, LoginDto.class);
            
        }catch(IOException e){
            System.out.println(e);
        }

        try {
            if (!(userDao.getUserIsActivated(loginDto.getEmail()))){
                return Response.status(Response.Status.PRECONDITION_FAILED).entity("Email nincs aktiválva!").build();
            }
        } catch (UserNotFoundException ex) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Email vagy jelszó nem megfelelő!").build();
        }
      
        
        try{
            req.getSession();
            req.login(loginDto.getEmail(), loginDto.getPassword());
        }catch(ServletException e){
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Email vagy jelszó nem megfelelő!").build();
        }
        
        UserDetailDto userDetailDto = new UserDetailDto(securityContext.getUserPrincipal().getName(), getRole(securityContext) );
        
        
        Mapper<UserDetailDto> resultMapper = new Mapper();
        String result = resultMapper.convertToJson(userDetailDto);
        return Response.ok(result).build();
    }
    
    
    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@Context final SecurityContext securityContext, @Context HttpServletRequest req, String dto) throws ServletException, IOException {
        Mapper<LoginDto> mapper = new Mapper();
        
        LoginDto loginDto = new LoginDto();
        try{
            loginDto = mapper.get(dto, LoginDto.class);
        }catch(IOException e){
            System.out.println(e);
        }
        
        try {
            userDao.registerUser(loginDto);
        } catch (EmailAlreadyExistException ex) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Email address is already used").build();
        }
        
        
        return Response.ok(loginDto.getEmail()).build();
    }
    
    @POST
    @Path("newpassword")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newPassword(@Context final SecurityContext securityContext, @Context HttpServletRequest req, String email){
        try{
            User user = userDao.newPassword(email);
        } catch (UserNotFoundException ex) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Nincs ilyen regisztrált email cím").build();
        }
        return Response.ok(email).build();
    }
    @POST
    @Path("changepassword")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword(@Context final SecurityContext securityContext, @Context HttpServletRequest req, String credentials){
        User user = null;
        
        Mapper<ChangePasswordDto> resultMapper = new Mapper();
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        
        try {
            changePasswordDto = resultMapper.get(credentials, ChangePasswordDto.class);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            String email = securityContext.getUserPrincipal().getName();
            user = userDao.changePassword(email, changePasswordDto.getOldpassword(), changePasswordDto.getNewpassword());
        } catch (UserNotFoundException ex) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Nincs ilyen regisztrált email cím").build();
        } catch (OldPasswordMisMatchException ex) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("A megadott régi jelszó nem egyezik!").build();
        }
        return Response.ok(user.getEmail()).build();
    }
    
    @POST
    @Path("activate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response activate(@Context final SecurityContext securityContext, @Context HttpServletRequest req, String activationCode){
        
        
        
        String email = null;
        
        try{
            email = activateUser(activationCode);
        }catch(NoResultException e){
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Email not found").build();
        } catch (AlreadyActivatedException ex) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Email already activated").build();
        } catch (RegistrationCodeMisMatchException ex) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Registration code mismatch").build();
        } catch (UserNotFoundException ex) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity(ex.getMessage()).build();
        }
        
        return Response.ok(email).build();
    }
    
    private String activateUser(String activationString) throws NoResultException, AlreadyActivatedException, RegistrationCodeMisMatchException, UserNotFoundException{
        
        return userDao.activateUser(activationString);
       
    }
    
    @POST
    @Path("logout")
    public Response logout(@Context final SecurityContext securityContext, @Context HttpServletRequest req) throws ServletException {
        req.getSession();
        req.logout();
        return Response.ok().build();
    }
    
    @GET 
    @Path("userDetails")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserDetails(@Context final SecurityContext securityContext, @Context HttpServletRequest req){
        if (securityContext.getUserPrincipal() == null){
             return Response.status(Response.Status.PRECONDITION_FAILED).entity("Not logged in").build();
        }
        
        UserDetailDto userDetailDto = new UserDetailDto(securityContext.getUserPrincipal().getName(), getRole(securityContext));
        
        Mapper<UserDetailDto> resultMapper = new Mapper();
        String result;
        try {
            result = resultMapper.convertToJson(userDetailDto);
        } catch (IOException ex) {
            //????
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Not logged in").build();
        }
        
        return Response.ok(result).build(); 
    }
    
    @GET
    @Path("onlyAdmin")
    @RolesAllowed(SecGroup.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response onlyAdmin(){
        return Response.ok().build(); 
    }
    
    @GET
    @Path("onlyUser")
    @RolesAllowed(SecGroup.USER)
    @Produces(MediaType.APPLICATION_JSON)
    public Response onlyUser(){
        return Response.ok().build(); 
    }

    private String getRole(SecurityContext securityContext) {
        if (securityContext.isUserInRole(SecGroup.USER)){
            return SecGroup.USER;
        }
        if (securityContext.isUserInRole(SecGroup.ADMIN)){
            return SecGroup.ADMIN;
        }
        
        return null;
    }

    
    
}
