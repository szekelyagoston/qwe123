/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import szakemberkereso.entities.basedata.IEntityWithValueList;
import szakemberkereso.entities.checkboxdatas.IBaseExpandebaleValueList;
import szakemberkereso.specialists.dao.BaseValueListDao;
import szakemberkereso.specialists.dto.IBaseValueListDto;
import szakemberkereso.specialists.dto.SpecialistDto;

/**
 *
 * @author gusztafszon
 * T -> problem, K-y problemDto
 * @param <T> 
 * @param <K>
 */
public class ExpandebleValueList<T extends IBaseExpandebaleValueList, K extends IBaseValueListDto> {
    
    Class<T> clazz;
    EntityFactory<T> entityFactory;
    BaseValueListDao<T> dao;
    //should be working for office and specialist as well...Superclass, stb.
    IEntityWithValueList<T> specialist;

    public ExpandebleValueList(Class<T> clazz, IEntityWithValueList<T> sp, BaseValueListDao<T> dao) {
        this.clazz = clazz;
        this.specialist = sp;
        this.dao = dao;
        entityFactory = new EntityFactory<>(clazz);
    }

    List<T> newPoblem = new ArrayList<>();
    List<T> oldProblems = new ArrayList<>();
        
    public <J extends SpecialistDto> void runCopy(J specialistDto, List<K> problems) throws InstantiationException, IllegalAccessException{
        for (Iterator<K> it = problems.iterator(); it.hasNext();) {
                K problem = it.next();
                if (problem.getId()== null){
                    T nProblem = entityFactory.getInstance();
                    T existingNotVerifiedProblem = dao.findByStringValue(problem.getValue());
                    if (existingNotVerifiedProblem == null){
                        nProblem.setValue(problem.getValue());
                        newPoblem.add(nProblem);
                    }else{
                        nProblem = existingNotVerifiedProblem;
                        oldProblems.add(nProblem);
                    }

                }else{
                    T existing = dao.findById(problem.getId());

                    oldProblems.add(existing);
                }
                it.remove();
            }
        
        specialist.clearValueListWithType(clazz);
        if (newPoblem.size() > 0){
            specialist.addValueListSet(dao.persistSet(newPoblem));
        }
        if (oldProblems.size() > 0){
            //if already added, we should NOT ad again
            specialist.addValueListSet(oldProblems);
        }
   
    };
    

}
