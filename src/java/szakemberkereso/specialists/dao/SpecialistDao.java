/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import szakemberkereso.entities.basedata.Document;
import szakemberkereso.entities.basedata.Office;
import szakemberkereso.entities.basedata.Specialist;
import szakemberkereso.specialists.dto.SymptomDto;

/**
 *
 * @author gusztafszon
 */
@Stateless
public class SpecialistDao {
    
    @PersistenceContext(unitName = "SzakemberkeresoPU")
    private EntityManager em;
    
    public Specialist updateSpecialist(Specialist specialist){
        return em.merge(specialist);
    }
    
    public Document getDocumentById(Long id) throws NoResultException{
        return em.createNamedQuery("Document.findById", Document.class).setParameter("id", id).getSingleResult();
    }
    
    public List<Specialist> getAllIfOfficeExist() throws NoResultException{
        return em.createNamedQuery("Specialist.findAllIfOfficeExistAndOfficeIsVerified", Specialist.class).getResultList();
    }

    public List<Specialist> getBySymptomIfOfficeExist(SymptomDto symptom) {
        return em.createNamedQuery("Specialist.findBySymptomIfOfficeExistAndOfficeIsVerified", Specialist.class).setParameter("symptomId", symptom.getSymptomId()).getResultList();
    }
    
    public List<Specialist> getAllNotVerifiedOrModifiedSpecialist(){
        return em.createNamedQuery("Specialist.getAllNotVerifiedOrModified", Specialist.class).getResultList();
    }
    
    public Specialist getSpecialistByEmail(String email){
        return em.createNamedQuery("Specialist.getByEmail", Specialist.class).setParameter("email", email).getSingleResult();
    }
    

}
