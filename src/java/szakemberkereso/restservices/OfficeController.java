/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.restservices;

import com.sun.faces.util.ReflectionUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.modelmapper.ModelMapper;
import szakemberkereso.entites.security.User;
import szakemberkereso.entities.basedata.BaseOffice;
import szakemberkereso.entities.basedata.BaseOpeningHours;
import szakemberkereso.entities.basedata.ModifiedOffice;
import szakemberkereso.entities.basedata.Office;
import szakemberkereso.entities.basedata.OpeningHours;
import szakemberkereso.entities.basedata.Specialist;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.enums.SecGroup;
import szakemberkereso.helpers.Mapper;
import szakemberkereso.specialists.dao.OfficeDao;
import szakemberkereso.specialists.dao.ProblemDao;
import szakemberkereso.specialists.dto.OfficeDto;
import szakemberkereso.specialists.dto.OpeningHoursDto;
import szakemberkereso.specialists.dto.SpecialistWithOfficesDto;
import szakemberkereso.users.dao.UserDao;

/**
 *
 * @author gusztafszon
 */
@Stateless
@Path("office")
public class OfficeController {
    
    @Inject
    OfficeDao officeDao;
    
    @Inject 
    UserDao userDao;
    
//    @Inject 
//    ProblemDao problemDao;
    
    @GET 
    @Path("getById/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({SecGroup.ADMIN, SecGroup.USER})
    public Response getCurrentSpecialist(@Context final SecurityContext securityContext, @Context HttpServletRequest req, @PathParam("id") Long id){
   
        ModelMapper modelMapper = new ModelMapper();
        Mapper<OfficeDto> resultMapper = new Mapper();
        
        Office office = officeDao.getOfficeById(id);
        
        OfficeDto officeDto = modelMapper.map(office, OfficeDto.class);
        
        String result = null;
        
        try {
            result = resultMapper.convertToJson(officeDto);
        } catch (IOException ex) {
            Logger.getLogger(OfficeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return Response.ok(result).build(); 
    }
    
   
    
    @POST 
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({SecGroup.ADMIN, SecGroup.USER})
    public Response createOffice(@Context final SecurityContext securityContext, @Context HttpServletRequest req, String dto){
   
        ModelMapper modelMapper = new ModelMapper();
        Mapper<OfficeDto> resultMapper = new Mapper();
        User user = getCurrentUserByEmail(securityContext);
        
        
        OfficeDto officeDto = new OfficeDto();
        
        try {
            officeDto = resultMapper.get(dto, OfficeDto.class);
        } catch (IOException ex) {
            Logger.getLogger(OfficeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Office office = new Office();
        
        office = modelMapper.map(officeDto, Office.class);
        office.setDenied(false);
        office.setVerified(false);
        office.setModified(Boolean.FALSE);
        office.setSpecialist(user.getSpecialist());

        user.getSpecialist().addOffice(office);
        
        
        List<? extends BaseOpeningHours> dtoOpeningHours = office.getOpeningHours();
        office.setOpeningHours(new ArrayList<OpeningHours>());
        
        for (OpeningHoursDto openingHourDto : officeDto.getOpeningHours()){

            OpeningHours openingHour= new OpeningHours();
            openingHour.setFromClock(openingHourDto.getFromClock());
            openingHour.setToClock(openingHourDto.getToClock());
            openingHour.setOpeningDay(openingHourDto.getOpeningDay());
            openingHour.setOffice(office);
            
            office.addOpeningHour(openingHour);

            
        }
        
        
        officeDao.persist(office);
        
        officeDto = modelMapper.map(office, OfficeDto.class);
        
        String result = null;
        
        try {
            result = resultMapper.convertToJson(officeDto);
        } catch (IOException ex) {
            Logger.getLogger(OfficeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return Response.ok(result).build(); 
    }
    
    @POST 
    @Path("modifyOffice/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({SecGroup.ADMIN, SecGroup.USER})
    public Response modifyOffice(@Context final SecurityContext securityContext, @Context HttpServletRequest req, String dto,  @PathParam("id") Long id){
   
        ModelMapper modelMapper = new ModelMapper();
        Mapper<OfficeDto> resultMapper = new Mapper();

        
        
        OfficeDto officeDto = new OfficeDto();
        
        try {
            officeDto = resultMapper.get(dto, OfficeDto.class);
        } catch (IOException ex) {
            Logger.getLogger(OfficeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Office oldOffice = officeDao.getOfficeById(id);
        //Office office = null; 
        
        //office = modelMapper.map(officeDto, Office.class);
        
        if (oldOffice.getVerified()){
            copyFields(officeDto,oldOffice.getModifiedOffice());
            oldOffice.getModifiedOffice().setOffice(oldOffice);
            oldOffice.setDenied(Boolean.FALSE);
            oldOffice.setDeniedMessage(null);
            oldOffice.setModified(Boolean.TRUE);
            
        }else{
            copyFields(officeDto,oldOffice);
            oldOffice.setDenied(Boolean.FALSE);
            oldOffice.setDeniedMessage(null);
        }
        
        
        
        officeDao.merge(oldOffice);
        
        officeDto = modelMapper.map(oldOffice, OfficeDto.class);
        
        String result = null;
        
        try {
            result = resultMapper.convertToJson(officeDto);
        } catch (IOException ex) {
            Logger.getLogger(OfficeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return Response.ok(result).build(); 
    }
    
    private User getCurrentUserByEmail(SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        User user = userDao.getUserByEmail(email);
        return user;
    }
    
    //source, destination
    private <T extends BaseOffice> void copyFields(OfficeDto office, T oldOffice) {
        
        oldOffice.setAddress(office.getAddress());
        oldOffice.setLat(office.getLat());
        oldOffice.setLng(office.getLng());
       

        oldOffice.getOpeningHours().clear();
        for (OpeningHoursDto openingHourDto : office.getOpeningHours()){
            oldOffice.addOpeningHourFromDto(openingHourDto);
        }      
        officeDao.persistOpeningHourSet(oldOffice.getOpeningHours());

        oldOffice.setUserGivenAddress(office.getUserGivenAddress());
    }
}
