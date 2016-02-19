/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.basedata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name="Specialist")
@NamedQueries({
    @NamedQuery(name="Specialist.findAllIfOfficeExistAndOfficeIsVerified",query="SELECT DISTINCT s FROM Specialist s JOIN s.officeList o WHERE s.officeList IS NOT EMPTY AND o.verified = TRUE AND s.verified = TRUE"),
    @NamedQuery(name="Specialist.findBySymptomIfOfficeExistAndOfficeIsVerified",query="SELECT DISTINCT s FROM Specialist s JOIN s.officeList o JOIN s.problems p JOIN p.connectedSymptom sy WHERE s.officeList IS NOT EMPTY AND sy.symptomId = :symptomId AND o.verified = TRUE AND s.verified = TRUE"),
    @NamedQuery(name="Specialist.getAllNotVerified",query="SELECT s FROM Specialist s WHERE s.verified = FALSE AND s.firstModificationDone = TRUE"),
    @NamedQuery(name="Specialist.getByEmail",query="SELECT s FROM Specialist s WHERE s.user.email = :email"),
    @NamedQuery(name="Specialist.getAllNotVerifiedOrModified",query="SELECT s FROM Specialist s WHERE (s.verified = FALSE AND s.firstModificationDone = TRUE) OR s.modified = TRUE"),
})
public class Specialist extends BaseSpecialist{
   
    @OneToOne(optional = true, mappedBy = "specialist", cascade = CascadeType.ALL, orphanRemoval = true)
    private ModifiedSpecialist modifiedSpecialist;
    
    @Column(name="first_modification_done")
    private Boolean firstModificationDone;
     
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="user_id")
    private User user;
    
    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    private List<Office> officeList;
    
    @Column(name="modified")
    private Boolean modified;
    
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name="Specialist_Problem", joinColumns=@JoinColumn(name="specialist_id"), inverseJoinColumns=@JoinColumn(name="problem_id"))  
    private List<Problem> problems;
    
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name="Specialist_Qualification", joinColumns=@JoinColumn(name="specialist_id"), inverseJoinColumns=@JoinColumn(name="id"))  
    private List<Qualification> qualifications;

    public Specialist() {
        super();
        officeList = new ArrayList<>();
        qualifications = new ArrayList<>();
        this.firstModificationDone = false;
        this.modifiedSpecialist = new ModifiedSpecialist(this);
        this.modified = false;
        this.problems = new ArrayList<>();
    }

    public Specialist(User user) {
        super(user);
        officeList = new ArrayList<>();
        qualifications = new ArrayList<>();
        this.firstModificationDone = false;
        this.modifiedSpecialist = new ModifiedSpecialist(this);
        this.user = user;
        this.modified = false;
        this.problems = new ArrayList<>();
    }


    
    public ModifiedSpecialist getModifiedSpecialist() {
        return modifiedSpecialist;
    }

    public void setModifiedSpecialist(ModifiedSpecialist modifiedSpecialist) {
        this.modifiedSpecialist = modifiedSpecialist;
    }
    
    public Boolean getFirstModificationDone() {
        return firstModificationDone;
    }

    public void setFirstModificationDone(Boolean firstModificationDone) {
        this.firstModificationDone = firstModificationDone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Office> getOfficeList() {
        return officeList;
    }

    public void setOfficeList(List<Office> officeList) {
        this.officeList = officeList;
    }
    
    public void addOffice(Office office){
        this.officeList.add(office);
    }
    
    public String getEmail(){
        return this.user.getEmail();
    }

    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    public List<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<Qualification> qualifications) {
        this.qualifications = qualifications;
    }
    
    
    
    
    
    
    @Override
     public List<Problem> getProblems() {
        return problems;
    }

    @Override
    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }
    
    @Override
     public void addProblem(Problem problem){
        this.problems.add(problem);
    }
    
    @Override
    public void addProblemSet(List<Problem> problems){
        this.problems.addAll(problems);
    }
    
     @Override
    public void addQualificationSet(List<Qualification> qualifications) {
        this.qualifications.addAll(qualifications);
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
    public void clearValueListWithType(Class clazz) {
        //NOT GOOD LOOK UP
        if (clazz.isInstance(Problem.class)){
            this.problems.clear();
        }else if (clazz.isInstance(Qualification.class)){
            this.qualifications.clear();
        }
    }

   

   
    
    
}
