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
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.entities.checkboxdatas.Qualification;
import szakemberkereso.entities.checkboxdatas.Symptom;

/**
 *
 * @author gusztafszon
 */
@Stateless
public class QualificationDao implements BaseValueListDao<Qualification>{

    @PersistenceContext(unitName = "SzakemberkeresoPU")
    private EntityManager em;
    
    @Override
    public Qualification findByStringValue(String value) {
        List<Qualification> qualifications = em.createNamedQuery("Qualification.findByStringValue", Qualification.class).setParameter("stringValue", value).getResultList();
        if (qualifications.size() > 0) {
            return qualifications.get(0);
        }
        return null;
    }

    @Override
    public Qualification findById(Long id) {
        return em.find(Qualification.class, id);
    }

    @Override
    public List<Qualification> getAll() {
        return em.createNamedQuery("Qualification.getAll", Qualification.class).getResultList();
    }

    @Override
    public List<Qualification> persistSet(List<Qualification> newQualifications) {
        List<Qualification> results = new ArrayList<>();
        for (Qualification qualification : newQualifications){
            em.persist(qualification);
            results.add(qualification);
        }
        return results;
    }

    @Override
    public List<Qualification> mergeSet(List<Qualification> oldQualifications) {
        List<Qualification> results = new ArrayList<>();
        for (Qualification qualification : oldQualifications){
            results.add(em.merge(qualification));
        }
        return results;
    }

    @Override
    public List<Qualification> getAllVerified() {
        return em.createNamedQuery("Qualification.getAllVerified", Qualification.class).getResultList();
    }

}
