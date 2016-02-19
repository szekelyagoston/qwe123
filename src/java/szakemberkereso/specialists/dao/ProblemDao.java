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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.entities.checkboxdatas.Symptom;

/**
 *
 * @author gusztafszons
 */
@Stateless
public class ProblemDao implements BaseValueListDao<Problem>{
    @PersistenceContext(unitName = "SzakemberkeresoPU")
    private EntityManager em;
    
    @Override
    public List<Problem> getAll(){
        return em.createNamedQuery("Problem.getAll", Problem.class).getResultList();
    }
    
    @Override
    public List<Problem> getAllVerified(){
        return em.createNamedQuery("Problem.getAllVerified", Problem.class).getResultList();
    }
    
    @Override
    public List<Problem> mergeSet(List<Problem> oldProblems) {
        List<Problem> results = new ArrayList<>();
        for (Problem problem : oldProblems){
            results.add(em.merge(problem));
        }
        return results;
    }

    public Problem findProblem(Long id) {
        return em.find(Problem.class, id);
    }

    @Override
    public List<Problem> persistSet(List<Problem> newPoblem) {
        List<Problem> results = new ArrayList<>();
        for (Problem problem : newPoblem){
            em.persist(problem);
            results.add(problem);
        }
        return results;
    }

    public Problem findProblemByStringValue(String stringValue) throws NoResultException{
        
        List<Problem> problem = em.createNamedQuery("Problem.findByStringValue", Problem.class).setParameter("stringValue", stringValue).getResultList();
        if (problem.size() > 0) {
            return problem.get(0);
        }
        return null;
        
    }

    @Override
    public Problem findByStringValue(String value) {
        return findProblemByStringValue(value);
    }

    @Override
    public Problem findById(Long id) {
        return findProblem(id);
    }

    
}
