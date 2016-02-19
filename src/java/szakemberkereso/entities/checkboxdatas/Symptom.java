/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.checkboxdatas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gusztafszon
 */
@Entity
@Table(name="symptom")
@NamedQueries({
    @NamedQuery(name="Symptom.findById",query="SELECT c FROM Symptom c WHERE c.symptomId = :symptomId"),
    @NamedQuery(name="Symptom.getAll",query="SELECT c FROM Symptom c")
})
public class Symptom implements Serializable{
    
    @Id @GeneratedValue
    @Column(name="symptom_id")
    private Long symptomId;

    @Column(name="stringValue")
    private String stringValue;  

    @ManyToMany(mappedBy="connectedSymptom")
    private List<Problem> connectedProblem;

    public Symptom() {
        connectedProblem = new ArrayList<>();
    }
    
    

    public List<Problem> getConnectedProblem() {
        return connectedProblem;
    }

    public void setConnectedProblem(List<Problem> connectedProblem) {
        this.connectedProblem = connectedProblem;
    }
    
    public void addConnectedProblem(Problem problem){
        this.connectedProblem.add(problem);
    }

    public Long getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(Long symptomId) {
        this.symptomId = symptomId;
    }

    
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
    
    
  
  
    
}
