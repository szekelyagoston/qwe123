/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dao;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import szakemberkereso.entities.basedata.BaseOffice;
import szakemberkereso.entities.basedata.BaseOpeningHours;
import szakemberkereso.entities.basedata.Office;
import szakemberkereso.entities.basedata.OpeningHours;
import szakemberkereso.entities.checkboxdatas.Problem;

/**
 *
 * @author gusztafszon
 */
@Stateless
public class OfficeDao {
    
    @PersistenceContext(unitName = "SzakemberkeresoPU")
    private EntityManager em;
    
    public Office getOfficeById(Long id){
        return em.createNamedQuery("Office.findById", Office.class).setParameter("id", id).getSingleResult();
    }
    
    public List<Office> getAllOffice(){
        return em.createNamedQuery("Office.getAll", Office.class).getResultList();
    }
    
    public List<Office> getAllNotVerifiedOffice(){
        return em.createNamedQuery("Office.getAllNotVerified", Office.class).getResultList();
    }
    
    
    public Office persist(Office office){
            em.persist(office);
        return office;
    }
    
    public Office merge(Office office){
            em.merge(office);
        return office;
    }
    
    public OpeningHours getOpeninghourById(Long id){
        return em.createNamedQuery("OpeningHours.findById", OpeningHours.class).setParameter("id", id).getSingleResult();
    }

//    public List<OpeningHours> persistOpeningHourSet(List<OpeningHours> newOpeningHour) {
//        List<OpeningHours> results = new ArrayList<>();
//        for (OpeningHours openingHour : newOpeningHour){
//            em.persist(openingHour);
//            results.add(openingHour);
//        }
//        return results;
//    }
    
    public <T extends BaseOpeningHours> List<T> persistOpeningHourSet(List<T> newOpeningHour) {
        List<T> results = new ArrayList<>();
        for (T openingHour : newOpeningHour){
            em.persist(openingHour);
            results.add(openingHour);
        }
        return results;
    }

    public Iterable<Office> getAllNotVerifiedOrModifiedOffice() {
        return em.createNamedQuery("Office.getAllNotVerifiedOrModified", Office.class).getResultList();
    }
    
}
