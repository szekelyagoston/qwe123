/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.restservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import szakemberkereso.entites.security.User;
import szakemberkereso.entities.basedata.Specialist;
import szakemberkereso.enums.SecGroup;
import szakemberkereso.helpers.Mapper;
import szakemberkereso.search.dto.SearchDto;
import szakemberkereso.specialists.dao.SpecialistDao;
import szakemberkereso.specialists.dto.SpecialistWithOfficesDto;
import szakemberkereso.specialists.dto.SpecialistWithVerifiedOfficesDto;

/**
 *
 * @author gusztafszon
 */
@Stateless
@Path("search")
public class SearchController {
    
    @Inject
    SpecialistDao specialistDao;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchSpecialist(@Context final SecurityContext securityContext, @Context HttpServletRequest req, SearchDto dto) {
        
        ModelMapper modelMapper = new ModelMapper();
        
        List<SpecialistWithVerifiedOfficesDto> result = new ArrayList<>();
        List<Specialist> foundEntities = new ArrayList<>();
        
        if (dto.getSymptom() == null){
            foundEntities =  specialistDao.getAllIfOfficeExist();
        }else{
            foundEntities = specialistDao.getBySymptomIfOfficeExist(dto.getSymptom());
        }
        
        for(Specialist specialist : foundEntities){
            SpecialistWithVerifiedOfficesDto specialistDto = modelMapper.map(specialist, SpecialistWithVerifiedOfficesDto.class);
            //should be set in modelmapper
            specialistDto.setPicture(specialist.getPicture() == null ? null : Base64.encodeBase64String(specialist.getPicture()));
            result.add(specialistDto);
        }

        return Response.ok(new GenericEntity<List<SpecialistWithVerifiedOfficesDto>>(result) {}).build(); 
    }
}
