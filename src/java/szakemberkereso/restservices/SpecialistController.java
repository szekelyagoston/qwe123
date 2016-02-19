/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.restservices;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.modelmapper.ModelMapper;
import szakemberkereso.entites.security.User;
import szakemberkereso.entities.basedata.BaseSpecialist;
import szakemberkereso.entities.basedata.Document;
import szakemberkereso.entities.basedata.Specialist;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.entities.checkboxdatas.Qualification;
import szakemberkereso.enums.SecGroup;
import szakemberkereso.helpers.ExpandebleValueList;
import szakemberkereso.helpers.Mapper;
import szakemberkereso.specialists.dao.BaseValueListDao;
import szakemberkereso.specialists.dao.SpecialistDao;
import szakemberkereso.specialists.dto.ProblemDto;
import szakemberkereso.specialists.dto.QualificationDto;
import szakemberkereso.specialists.dto.SpecialistWithOfficesDto;
import szakemberkereso.users.dao.UserDao;

/**
 *
 * @author gusztafszon
 */
@Stateless
@Path("specialist")
public class SpecialistController {

    @Inject
    UserDao userDao;

    @Inject
    SpecialistDao specialistDao;
    
    @Inject
    BaseValueListDao<Problem> problemDao;
    
    @Inject
    BaseValueListDao<Qualification> qualificationDao;

    @GET
    @Path("current/details")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({SecGroup.ADMIN, SecGroup.USER})
    public Response getCurrentSpecialist(@Context final SecurityContext securityContext, @Context HttpServletRequest req) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        Mapper<SpecialistWithOfficesDto> resultMapper = new Mapper();
        SpecialistWithOfficesDto specialistDto = new SpecialistWithOfficesDto();

        if (securityContext.getUserPrincipal()
                == null) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("Not logged in").build();
        }

        User user = getCurrentUserByEmail(securityContext);

        Specialist specialist = user.getSpecialist();

        specialistDto = modelMapper.map(specialist, SpecialistWithOfficesDto.class);
        specialistDto.setPicture(specialist.getPicture() == null ? null : Base64.encodeBase64String(specialist.getPicture()));

        return Response.ok(new GenericEntity<SpecialistWithOfficesDto>(specialistDto) {}).build(); 
    }

    @POST
    @Path("current/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({SecGroup.ADMIN, SecGroup.USER})
    public Response updateCurrentSpecialist(@Context
            final SecurityContext securityContext, @Context HttpServletRequest req, SpecialistWithOfficesDto specialistDto) {
        //could be a generic-string-dto-map.to.entity-dto-string
        Mapper<SpecialistWithOfficesDto> resultMapper = new Mapper();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        User user = getCurrentUserByEmail(securityContext);

        Specialist specialist = user.getSpecialist();
        
        //modification
        if (specialist.getVerified()){
            deepCopy(specialist.getModifiedSpecialist(), specialistDto);
            specialist.setDenied(Boolean.FALSE);
            specialist.setDeniedMessage(null);
            specialist.setModified(Boolean.TRUE);
            
        }else{
            deepCopy(specialist, specialistDto);
            specialist.setFirstModificationDone(Boolean.TRUE);
            specialist.setDenied(Boolean.FALSE);
            specialist.setDeniedMessage(null);
        }
        
        
        specialist = specialistDao.updateSpecialist(specialist);

        specialistDto = modelMapper.map(specialist, SpecialistWithOfficesDto.class);

        String result = null;

        try {
            result = resultMapper.convertToJson(specialistDto);
        } catch (IOException ex) {
            Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.ok(result)
                .build();
    }

    @GET
    @Path("current/getDocumentById/{id}")
    @Produces("application/pdf")
    public Response downloadPictureForTest(@Context
            final SecurityContext securityContext, @Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") Long id) {

        User user = getCurrentUserByEmail(securityContext);
        Document document = null;
        try{
            document = specialistDao.getDocumentById(id);
        }catch(NoResultException ex){
            Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (!document.getSpecialist().getUser().equals(user)){
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("You have no right to download this file").build();
        }
        
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment;filename=" + document.getTitle());

        ServletOutputStream out = null;
        try {
            out = resp.getOutputStream();
            out.write(document.getValueInByte());
            out.flush();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return Response.ok()
                .build();
        
    }
    

    @POST
    @Path("current/update/picture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({SecGroup.ADMIN, SecGroup.USER})
    public Response updateCurrentPicture(@Context
            final SecurityContext securityContext, @Context HttpServletRequest req, @FormDataParam("file") InputStream uploadedInputStream) {
        
        //could be a generic-string-dto-map.to.entity-dto-string
        Mapper<SpecialistWithOfficesDto> resultMapper = new Mapper();
        ModelMapper modelMapper = new ModelMapper();

        User user = getCurrentUserByEmail(securityContext);

        SpecialistWithOfficesDto specialistDto = null;

        Specialist specialist = user.getSpecialist();
        
        byte[] bytes;
        try {
            bytes = IOUtils.toByteArray(uploadedInputStream);
        } catch (IOException ex) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("OOPS").build();
        }
        
        if (specialist.getVerified()){
            specialist.getModifiedSpecialist().setPicture(bytes);
            specialist.getModifiedSpecialist().setDenied(Boolean.FALSE);
            specialist.getModifiedSpecialist().setDeniedMessage(null);
            
        }else{
            specialist.setPicture(bytes);
            specialist.setFirstModificationDone(Boolean.TRUE);
            specialist.setDenied(Boolean.FALSE);
            specialist.setDeniedMessage(null);
        }
        
        
        
        specialist = specialistDao.updateSpecialist(specialist);

        specialistDto = modelMapper.map(specialist, SpecialistWithOfficesDto.class);

        String result = null;

        try {
            result = resultMapper.convertToJson(specialistDto);
        } catch (IOException ex) {
            Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.ok(result)
                .build();
    }
    
    
    @POST
    @Path("current/update/document")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({SecGroup.ADMIN, SecGroup.USER})
    public Response uploadDocument(@Context
            final SecurityContext securityContext, @Context HttpServletRequest req, @FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("name") String name, @FormDataParam("comment") String comment) {
        
        //could be a generic-string-dto-map.to.entity-dto-string
        Mapper<SpecialistWithOfficesDto> resultMapper = new Mapper();
        ModelMapper modelMapper = new ModelMapper();

        User user = getCurrentUserByEmail(securityContext);

        SpecialistWithOfficesDto specialistDto = null;

        Specialist specialist = user.getSpecialist();
        byte[] bytes;
        try {
            bytes = IOUtils.toByteArray(uploadedInputStream);
        } catch (IOException ex) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity("OOPS").build();
        }
        Document document = new Document();
        document.setValueInByte(bytes);
        document.setComment(comment);
        document.setSpecialist(specialist);
        document.setTitle(name);
        specialist.addDocument(document);
        specialist = specialistDao.updateSpecialist(specialist);

        specialistDto = modelMapper.map(specialist, SpecialistWithOfficesDto.class);

        String result = null;

        try {
            result = resultMapper.convertToJson(specialistDto);
        } catch (IOException ex) {
            Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.ok(result)
                .build();
    }
    
    @GET 
    @Path("/all/officeExist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllSpecialistIfOfficeExist(@Context final SecurityContext securityContext, @Context HttpServletRequest req){
   
        ModelMapper modelMapper = new ModelMapper();
        Mapper<SpecialistWithOfficesDto> resultMapper = new Mapper();
        
        List<SpecialistWithOfficesDto> resultList = new ArrayList<>();

        
        for(Specialist specialist : specialistDao.getAllIfOfficeExist()){
            SpecialistWithOfficesDto specialistDto = modelMapper.map(specialist, SpecialistWithOfficesDto.class);
            //should be set in modelmapper
            specialistDto.setPicture(specialist.getPicture() == null ? null : Base64.encodeBase64String(specialist.getPicture()));
            resultList.add(specialistDto);
        }

        

        
        
        return Response.ok(new GenericEntity<List<SpecialistWithOfficesDto>>(resultList) {}).build(); 
    }
    

    private User getCurrentUserByEmail(SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        User user = userDao.getUserByEmail(email);
        return user;
    }

    //destination, source
    private void deepCopy(BaseSpecialist specialist, SpecialistWithOfficesDto specialistDto) {
        specialist.setFirstName(specialistDto.getFirstName());
        specialist.setLastName(specialistDto.getLastName());
        specialist.setPhoneNumber(specialistDto.getPhoneNumber());
        specialist.setAbout(specialistDto.getAbout());
        
        //this is the new stuff
        ExpandebleValueList<Problem, ProblemDto> expandanbleProblems = new ExpandebleValueList<>(Problem.class, specialist, problemDao);
        ExpandebleValueList<Qualification, QualificationDto> expandanbleQualifications = new ExpandebleValueList<>(Qualification.class, specialist, qualificationDao);
        try {
            expandanbleProblems.runCopy(specialistDto, specialistDto.getProblems());
            expandanbleQualifications.runCopy(specialistDto, specialistDto.getQualifications());
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
