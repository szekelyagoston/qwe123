/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import szakemberkereso.entities.checkboxdatas.Symptom;

/**
 *
 * @author gusztafszon
 */
public class SymptomDao {
    
     @PersistenceContext(unitName = "SzakemberkeresoPU")
    private EntityManager em;
    
    public List<Symptom> getAllSymptom(){
        return em.createNamedQuery("Symptom.getAll", Symptom.class).getResultList();
    }
}
