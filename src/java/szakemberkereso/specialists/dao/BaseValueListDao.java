/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dao;

import java.util.List;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.entities.checkboxdatas.Symptom;

/**
 *
 * @author gusztafszon
 */
public interface BaseValueListDao<T> {
    public T findByStringValue(String value);
    public T findById(Long id);
    public List<T> getAll();
    public List<T> persistSet(List<T> newPoblem);
    public List<T> mergeSet(List<T> oldProblems);
    public List<T> getAllVerified();

}
