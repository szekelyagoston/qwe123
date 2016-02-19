/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.restservices;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.modelmapper.ModelMapper;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.entities.checkboxdatas.Symptom;
import szakemberkereso.specialists.dao.BaseValueListDao;
import szakemberkereso.specialists.dao.SymptomDao;
import szakemberkereso.specialists.dto.SymptomDto;

/**
 *
 * @author gusztafszon
 */
@Stateless
@Path("public")
public class MainPageController {
    
    @Inject
    BaseValueListDao<Problem> problemDao;
    
    @Inject 
    SymptomDao symptomDao;
    
    @GET 
    @Path("getAllSymptoms")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllSymptoms(@Context final SecurityContext securityContext, @Context HttpServletRequest req){
   
        ModelMapper modelMapper = new ModelMapper();
        
        List<SymptomDto> resultList = new ArrayList<>();
        
        for (Symptom symptom : symptomDao.getAllSymptom()){
            SymptomDto symptomDto = modelMapper.map(symptom, SymptomDto.class);
            resultList.add(symptomDto);
        }
        
                
        return Response.ok(new GenericEntity<List<SymptomDto>>(resultList) {}).build(); 
    }
}
