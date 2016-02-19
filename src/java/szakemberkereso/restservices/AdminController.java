/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.restservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
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
import org.modelmapper.ModelMapper;
import szakemberkereso.entites.security.User;
import szakemberkereso.entities.basedata.BaseSpecialist;
import szakemberkereso.entities.basedata.Document;
import szakemberkereso.entities.basedata.ModifiedOffice;
import szakemberkereso.entities.basedata.ModifiedSpecialist;
import szakemberkereso.entities.basedata.Office;
import szakemberkereso.entities.basedata.Specialist;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.entities.checkboxdatas.Qualification;
import szakemberkereso.entities.checkboxdatas.Symptom;
import szakemberkereso.enums.SecGroup;
import szakemberkereso.helpers.Mapper;
import szakemberkereso.specialists.dao.BaseValueListDao;
import szakemberkereso.specialists.dao.OfficeDao;
import szakemberkereso.specialists.dao.ProblemDao;
import szakemberkereso.specialists.dao.QualificationDao;
import szakemberkereso.specialists.dao.SpecialistDao;
import szakemberkereso.specialists.dao.SymptomDao;
import szakemberkereso.specialists.dto.OfficeDto;
import szakemberkereso.specialists.dto.OfficeWithSpecialistDto;
import szakemberkereso.specialists.dto.ProblemDto;
import szakemberkereso.specialists.dto.ProblemWithConnectedSymptomsDto;
import szakemberkereso.specialists.dto.QualificationDto;
import szakemberkereso.specialists.dto.SpecialistDto;
import szakemberkereso.specialists.dto.SpecialistWithOfficesDto;
import szakemberkereso.specialists.dto.SymptomDto;

/**
 *
 * @author gusztafszon
 */
@Stateless
@Path("admin")
public class AdminController {
    
    @Inject
    BaseValueListDao<Problem> problemDao;
    
    @Inject
    OfficeDao officeDao;
    
    @Inject
    SpecialistDao specialistDao;
    
    @Inject
    SymptomDao symptomDao;
    
    @Inject
    BaseValueListDao<Qualification> qualificationDao;
    
    @GET 
    @Path("getAllProblemWithSymptoms")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllProblemWithSymptoms(@Context final SecurityContext securityContext, @Context HttpServletRequest req){
   
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        Mapper<ProblemWithConnectedSymptomsDto> resultMapper = new Mapper();
        
        List<ProblemWithConnectedSymptomsDto> resultList = new ArrayList<>();
        
        for (Problem problem : problemDao.getAll()){
            ProblemWithConnectedSymptomsDto problemDto = modelMapper.map(problem, ProblemWithConnectedSymptomsDto.class);
            resultList.add(problemDto);
        }
        
                
        return Response.ok(new GenericEntity<List<ProblemWithConnectedSymptomsDto>>(resultList) {}).build(); 
    }
    
    @GET 
    @Path("getAllSymptoms")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllSymptoms(@Context final SecurityContext securityContext, @Context HttpServletRequest req){
   
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        List<SymptomDto> resultList = new ArrayList<>();
        
        for (Symptom symptom : symptomDao.getAllSymptom()){
            SymptomDto symptomDto = modelMapper.map(symptom, SymptomDto.class);
            resultList.add(symptomDto);
        }
        
                
        return Response.ok(new GenericEntity<List<SymptomDto>>(resultList) {}).build(); 
    }
    
    @GET 
    @Path("getAllVerifiedProblems")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllVerifiedProblems(@Context final SecurityContext securityContext, @Context HttpServletRequest req){
   
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        List<ProblemDto> resultList = new ArrayList<>();
        
        for (Problem problem : problemDao.getAllVerified()){
            if (problem.getVerified()){
                ProblemDto problemDto =modelMapper.map(problem, ProblemDto.class);
                resultList.add(problemDto);
            }
            
        }
        
                
        return Response.ok(new GenericEntity<List<ProblemDto>>(resultList) {}).build(); 
    }
    
    @GET 
    @Path("getAllVerifiedQualifications")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllVerifiedQualifications(@Context final SecurityContext securityContext, @Context HttpServletRequest req){
   
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        List<QualificationDto> resultList = new ArrayList<>();
        
        //could be in the dto, using jackson
        for (Qualification qualification : qualificationDao.getAllVerified()){
            if (qualification.getVerified()){
                QualificationDto qualificationDto =modelMapper.map(qualification, QualificationDto.class);
                resultList.add(qualificationDto);
            }
            
        }
        
                
        return Response.ok(new GenericEntity<List<QualificationDto>>(resultList) {}).build(); 
    }
    
    
    
    
    @GET 
    @Path("getAllNotVerifiedProblems")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllNotVerifiedProblems(@Context final SecurityContext securityContext, @Context HttpServletRequest req){
   
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        List<ProblemDto> resultList = new ArrayList<>();
        
        for (Problem problem : problemDao.getAll()){
            if (!problem.getVerified()){
                ProblemDto problemDto =modelMapper.map(problem, ProblemDto.class);
                resultList.add(problemDto);
            }
            
        }
        
                
        return Response.ok(new GenericEntity<List<ProblemDto>>(resultList) {}).build(); 
    }
    
    @GET 
    @Path("getAllNotVerifiedQualifications")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllNotVerifiedQualifications(@Context final SecurityContext securityContext, @Context HttpServletRequest req){
   
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        List<QualificationDto> resultList = new ArrayList<>();
        
        //could be in the dto, using jackson
        for (Qualification qualification : qualificationDao.getAll()){
            if (!qualification.getVerified()){
                QualificationDto qualificationDto =modelMapper.map(qualification, QualificationDto.class);
                resultList.add(qualificationDto);
            }
            
        }
        
                
        return Response.ok(new GenericEntity<List<QualificationDto>>(resultList) {}).build(); 
    }
    
    
            
    @GET 
    @Path("getAllNotVerifiedOrModifiedOfficeWithSpecialist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllNotVerifiedOrModifiedOfficeWithSpecialist(@Context final SecurityContext securityContext, @Context HttpServletRequest req){
   
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        List<OfficeWithSpecialistDto> resultList = new ArrayList<>();
        
        //List<Office> officesNotVerified = officeDao.getAllNotVerifiedOffice();
        
        for (Office office : officeDao.getAllNotVerifiedOrModifiedOffice()){
            OfficeWithSpecialistDto officeDto = modelMapper.map(office, OfficeWithSpecialistDto.class);
            if (office.getSpecialist() != null){
                officeDto.setSpecialist(modelMapper.map(office.getSpecialist(), SpecialistDto.class));

                officeDto.getSpecialist().setPicture(office.getSpecialist().getPicture() == null ? null : Base64.encodeBase64String(office.getSpecialist().getPicture()));
                
            }
            
            resultList.add(officeDto);
        }
        
        
                
        return Response.ok(new GenericEntity<List<OfficeWithSpecialistDto>>(resultList) {}).build(); 
    }
    @GET 
    @Path("getAllNotVerifiedOrModifiedSpecialist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllNotVerifiedSpecialist(@Context final SecurityContext securityContext, @Context HttpServletRequest req){
   
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        List<SpecialistDto> resultList = new ArrayList<>();
        
        for (Specialist specialist : specialistDao.getAllNotVerifiedOrModifiedSpecialist()){
            SpecialistDto spceialistDto = modelMapper.map(specialist, SpecialistDto.class);

            spceialistDto.setPicture(specialist.getPicture() == null ? null : Base64.encodeBase64String(specialist.getPicture()));
            spceialistDto.getModifiedSpecialist().setPicture(spceialistDto.getModifiedSpecialist().getPicture() == null ? null : Base64.encodeBase64String(specialist.getModifiedSpecialist().getPicture()));
            resultList.add(spceialistDto);
        }
        
        
                
        return Response.ok(new GenericEntity<List<SpecialistDto>>(resultList) {}).build(); 
    }
    
    
    @POST 
    @Path("saveProblemSymptomRelation")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed(SecGroup.ADMIN)
    public Response saveProblemSymptomRelation(@Context final SecurityContext securityContext, @Context HttpServletRequest req, List<ProblemWithConnectedSymptomsDto> dto){
   
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        List<Problem> oldProblems = problemDao.getAll();
        

        
        for (ProblemWithConnectedSymptomsDto problemDto : dto){
            Problem problem = modelMapper.map(problemDto, Problem.class);
            Problem currentOldProblem = null;
            
            Boolean l = false;
            for (Problem oldProblem : oldProblems){
                if (Objects.equals(problem.getProblemId(), oldProblem.getProblemId())){
                     l = true;
                     currentOldProblem = oldProblem;
                     break;
                }
            }
            if (l){
                //update
                
                for (Iterator<Symptom> it = currentOldProblem.getConnectedSymptom().iterator(); it.hasNext();) {
                    Symptom oldSymptom = it.next();
                    Boolean k = false;
                    for (Symptom symptom : problem.getConnectedSymptom()){
                        if (Objects.equals(symptom.getSymptomId(), oldSymptom.getSymptomId())){
                                k = true;
                                break;
                            }
                    }
                    
                    if (!k){
                        //remove old item
                        //currentOldProblem.getConnectedSymptom().remove(oldSymptom);
                        it.remove();
                        oldSymptom.getConnectedProblem().remove(currentOldProblem);
                    }
                        
                }
                
                for (Iterator<Symptom> it = problem.getConnectedSymptom().iterator(); it.hasNext();) {
                    Symptom symptom = it.next();
                    Boolean j = false;
                    for (Iterator<Symptom> ite = currentOldProblem.getConnectedSymptom().iterator(); ite.hasNext();) {
                        Symptom oldSymptom = ite.next();
                        if (Objects.equals(symptom.getSymptomId(), oldSymptom.getSymptomId())){
                            j = true;
                            break;
                        }
                    }if (!j){
                            //add new item
                            currentOldProblem.addConnectedSymptom(symptom);
                            symptom.addConnectedProblem(currentOldProblem);
                    }
                }
            }else{
                //add new
                problem.setVerified(true);
                oldProblems.add(problem);
                for(Symptom symptom : problem.getConnectedSymptom()){
                    symptom.addConnectedProblem(problem);
                }
            }
        }
        
        problemDao.mergeSet(oldProblems);
        
                
        return getAllProblemWithSymptoms(securityContext, req);
    }
    
    @POST 
    @Path("verifyOffice/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({SecGroup.ADMIN})
    public Response verifyOffice(@Context final SecurityContext securityContext, @Context HttpServletRequest req, @PathParam("id") Long id){

        
        Office office = officeDao.getOfficeById(id);
        
        office.setVerified(Boolean.TRUE);
        office.setDenied(Boolean.FALSE);
        office.setModified(Boolean.FALSE);
        
        officeDao.merge(office);
        
        
        return getAllNotVerifiedOrModifiedOfficeWithSpecialist(securityContext, req);
    }
    
    @POST 
    @Path("verifyOfficeModification/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({SecGroup.ADMIN})
    public Response verifyOfficeModification(@Context final SecurityContext securityContext, @Context HttpServletRequest req, @PathParam("id") Long id){

        
        Office office = officeDao.getOfficeById(id);
        
        office.setVerified(Boolean.TRUE);
        office.setDenied(Boolean.FALSE);
        office.setModified(Boolean.FALSE);
        
        ModifiedOffice newOffice = office.getModifiedOffice();
        office.setModifiedOffice(new ModifiedOffice());
        
        deepCopy(newOffice, office);

        
        
        officeDao.merge(office);
        
        
        return getAllNotVerifiedOrModifiedOfficeWithSpecialist(securityContext, req);
    }
    
    @POST 
    @Path("denyOffice/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({SecGroup.ADMIN})
    public Response denyOffice(@Context final SecurityContext securityContext, @Context HttpServletRequest req, @PathParam("id") Long id, String message){

        
        Office office = officeDao.getOfficeById(id);
        
        office.setDenied(Boolean.TRUE);
        office.setDeniedMessage(message);
        
        officeDao.merge(office);
        
        
        return getAllNotVerifiedOrModifiedOfficeWithSpecialist(securityContext, req);
    }
    
    @POST 
    @Path("verifySpecialist/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({SecGroup.ADMIN})
    public Response verifySpecialist(@Context final SecurityContext securityContext, @Context HttpServletRequest req, @PathParam("email") String email){

        
        Specialist specialist = specialistDao.getSpecialistByEmail(email);
        
        specialist.setVerified(Boolean.TRUE);
        specialist.setDenied(Boolean.FALSE);
        
        specialistDao.updateSpecialist(specialist);
        
        
        return getAllNotVerifiedSpecialist(securityContext, req);
    }
    
    @POST 
    @Path("verifyModification/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({SecGroup.ADMIN})
    public Response verifyModification(@Context final SecurityContext securityContext, @Context HttpServletRequest req, @PathParam("email") String email){

        
        Specialist specialist = specialistDao.getSpecialistByEmail(email);
        
        specialist.setModified(Boolean.FALSE);
        specialist.setDenied(Boolean.FALSE);
        specialist.setDeniedMessage(null);
        
        ModifiedSpecialist newSpecialist = specialist.getModifiedSpecialist();
        specialist.setModifiedSpecialist(new ModifiedSpecialist());
        
        deepCopy(newSpecialist, specialist);
        
        specialistDao.updateSpecialist(specialist);
        
        
        return getAllNotVerifiedSpecialist(securityContext, req);
    }
    
    @POST 
    @Path("denySpecialist/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({SecGroup.ADMIN})
    public Response denySpecialist(@Context final SecurityContext securityContext, @Context HttpServletRequest req, @PathParam("email") String email, String message){

        
        Specialist specialist = specialistDao.getSpecialistByEmail(email);
        specialist.setDenied(Boolean.TRUE);
        specialist.setDeniedMessage(message);

        
        
        specialistDao.updateSpecialist(specialist);
        
        
        return getAllNotVerifiedSpecialist(securityContext, req);
    }
    
    @GET
    @Path("getDocumentById/{id}")
    @Produces("application/pdf")
    public Response downloadDocumentById(@Context
            final SecurityContext securityContext, @Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") Long id) {

        
        Document document = null;
        
        try{
            document = specialistDao.getDocumentById(id);
        }catch(NoResultException ex){
            Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
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
    @Path("verifyProblems")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verifyProblems(@Context
            final SecurityContext securityContext, @Context HttpServletRequest req, @Context HttpServletResponse resp, List<ProblemDto> dto) {

        List<Problem> newPoblem = new ArrayList<>();
        List<Problem> oldProblems = new ArrayList<>();
        for (Iterator<ProblemDto> it = dto.iterator(); it.hasNext();) {
            ProblemDto problem = it.next();
            if (problem.getProblemId() == null){
                Problem nProblem = new Problem();
                nProblem.setStringValue(problem.getStringValue());
                nProblem.setVerified(Boolean.TRUE);
                newPoblem.add(nProblem);
            }else{
                Problem oProblem = problemDao.findById(problem.getProblemId());
                oProblem.setVerified(Boolean.TRUE);
                oldProblems.add(oProblem);
            }
            it.remove();
        }
        
        problemDao.persistSet(newPoblem);
        problemDao.mergeSet(oldProblems);
        
         return getAllNotVerifiedProblems(securityContext, req);
        
    }
    
    
    //could be generic definetly
    @POST
    @Path("verifyQualifications")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verifyQualifications(@Context
            final SecurityContext securityContext, @Context HttpServletRequest req, @Context HttpServletResponse resp, List<QualificationDto> dto) {

        List<Qualification> newQualifications = new ArrayList<>();
        List<Qualification> oldQualifications = new ArrayList<>();
        for (Iterator<QualificationDto> it = dto.iterator(); it.hasNext();) {
            QualificationDto qualification = it.next();
            if (qualification.getId() == null){
                Qualification nQualification = new Qualification();
                nQualification.setStringValue(qualification.getStringValue());
                nQualification.setVerified(Boolean.TRUE);
                newQualifications.add(nQualification);
            }else{
                Qualification oQualification = qualificationDao.findById(qualification.getId());
                oQualification.setVerified(Boolean.TRUE);
                oldQualifications.add(oQualification);
            }
            it.remove();
        }
        
        qualificationDao.persistSet(newQualifications);
        qualificationDao.mergeSet(oldQualifications);
        
        return getAllNotVerifiedQualifications(securityContext, req);
        
    }
    
    
    

    private void deepCopy(ModifiedSpecialist source, Specialist target) {
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setAbout(source.getAbout());
        
        target.setProblems(source.getProblems());
        target.setQualifications(source.getQualifications());
        target.setPicture(source.getPicture());

    }

    private void deepCopy(ModifiedOffice source, Office target) {
        target.setAddress(source.getAddress());
        target.setLat(source.getLat());
        target.setLng(source.getLng());
        target.setUserGivenAddress(source.getUserGivenAddress());
        target.setOpeningHours(source.getOpeningHours());
        
    }
    
    
    
}
