/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.basedata;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import szakemberkereso.entites.security.User;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.entities.checkboxdatas.Qualification;

/**
 *
 * @author gusztafszon
 */
@Entity
@Table(name="Modified_Specialist")
public class ModifiedSpecialist extends BaseSpecialist{
    
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name="specialist_id")
    private Specialist specialist;
    
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name="Specialist_Problem_M", joinColumns=@JoinColumn(name="specialist_id"), inverseJoinColumns=@JoinColumn(name="problem_id"))  
    private List<Problem> modifiedProblems;
    
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name="Specialist_Qualification_M", joinColumns=@JoinColumn(name="specialist_id"), inverseJoinColumns=@JoinColumn(name="id"))  
    private List<Qualification> modifiedQualifications;

    public ModifiedSpecialist() {
        super();
    }
    
    public ModifiedSpecialist(Specialist sp) {
        super();
        this.specialist = sp;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public List<Problem> getModifiedProblems() {
        return modifiedProblems;
    }

    public void setModifiedProblems(List<Problem> modifiedProblems) {
        this.modifiedProblems = modifiedProblems;
    }

    public List<Qualification> getModifiedQualifications() {
        return modifiedQualifications;
    }

    public void setModifiedQualifications(List<Qualification> modifiedQualifications) {
        this.modifiedQualifications = modifiedQualifications;
    }
    
    
    
    @Override
    public List<Problem> getProblems() {
        return modifiedProblems;
    }

    @Override
    public void setProblems(List<Problem> problems) {
        this.modifiedProblems = problems;
    }
    
    @Override
     public void addProblem(Problem problem){
         
        this.modifiedProblems.add(problem);
    }
    
    @Override
    public void addProblemSet(List<Problem> problems){
        //no duplicate data will be added
        for (Problem problem : problems){
            if (!modifiedProblems.contains(problem)){
                this.modifiedProblems.add(problem);
            }
        }
        //this.modifiedProblems.addAll(problems);
    }

   

    @Override
    public void addValueListSet(List valueList) {

        if(valueList.get(0) instanceof Problem){
            addProblemSet(valueList);
        }else if (valueList.get(0) instanceof Qualification){
            addQualificationSet(valueList);
        }
        
    }

    @Override
    public void addQualificationSet(List<Qualification> qualifications) {
        for (Qualification qualification : qualifications){
            if (!modifiedQualifications.contains(qualification)){
                this.modifiedQualifications.add(qualification);
            }
        }
        //this.modifiedQualifications.addAll(qualifications);
    }

    @Override
    public void clearValueListWithType(Class clazz) {
        switch(clazz.getSimpleName()){
            case "Problem": {
                this.modifiedProblems.clear();
                break;
            }
            case "Qualification": {
                this.modifiedQualifications.clear();
                break;
            }
            default: { 
                break;
            }
        }
        
    }

    @Override
    public List<Qualification> getQualifications() {
        return this.getModifiedQualifications();
    }
    
    
    
}
