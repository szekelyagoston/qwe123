/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.checkboxdatas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import szakemberkereso.entities.basedata.BaseSpecialist;
import szakemberkereso.entities.basedata.Specialist;
import szakemberkereso.entities.basedata.Verifiable;

/**
 *
 * @author gusztafszon
 */
@Entity
@Table(name="problem")
@NamedQueries({
    @NamedQuery(name="Problem.findById",query="SELECT c FROM Problem c WHERE c.problemId = :problemId"),
    @NamedQuery(name="Problem.findByStringValue",query="SELECT c FROM Problem c WHERE c.stringValue = :stringValue"),
    @NamedQuery(name="Problem.getAll",query="SELECT c FROM Problem c"),
    @NamedQuery(name="Problem.getAllVerified",query="SELECT c FROM Problem c WHERE c.verified = TRUE")
    
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Problem extends VerifiableValueList implements IBaseExpandebaleValueList{
   
    @Id @GeneratedValue
    @Column(name="problem_id")
    private Long problemId;

    @Column(name="stringValue")
    private String stringValue;    
  
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="Symptom_Problem", joinColumns=@JoinColumn(name="problem_id"), inverseJoinColumns=@JoinColumn(name="symptom_id"))  
    private List<Symptom> connectedSymptom;

    public Problem() {
        super();
        connectedSymptom = new ArrayList<>();
    }
    
    

    public List<Symptom> getConnectedSymptom() {
        return connectedSymptom;
    }

    public void setConnectedSymptom(List<Symptom> connectedSymptom) {
        this.connectedSymptom = connectedSymptom;
    }
    
    public void addConnectedSymptom(Symptom symptom){
        this.connectedSymptom.add(symptom);
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public void setValue(String value) {
        this.setStringValue(value);
    }
    //helping jacksn out
    public Long getId(){
        return this.getProblemId();
    }

    
    
    
  
    
    
}
