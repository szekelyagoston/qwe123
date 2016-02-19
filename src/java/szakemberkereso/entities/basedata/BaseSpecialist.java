/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.basedata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import szakemberkereso.entites.security.User;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.entities.checkboxdatas.Qualification;

/**
 *
 * @author gusztafszon
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class BaseSpecialist extends Verifiable implements IEntityWithValueList{
    
    @Id @GeneratedValue
    @Column(name="id", nullable=false, updatable=false) 
    private Long id;
    
    @Column(name="first_name")
    private String firstName;
    
    @Column(name="last_name")
    private String lastName;
    
    @Column(name="picture")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;
    
    @Column(name="phone_number")
    private String phoneNumber;
     
    @OneToMany(mappedBy="specialist", cascade = CascadeType.ALL)
    private List<Document> documentList;
    
    @Column(name="about")
    private String about;
    
    public BaseSpecialist() {
        super();
        documentList = new ArrayList<>();
       
        
    }

    public BaseSpecialist(User user) {
        super();
        
        documentList = new ArrayList<>();


    }

    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }
    
    public void addDocument(Document document){
        this.documentList.add(document);
    }

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
    
    
    
    public abstract List<Problem> getProblems();
    
    public abstract List<Qualification> getQualifications();

    public abstract void setProblems(List<Problem> problems);
    
    public abstract void addProblem(Problem problem);
    
    public abstract void addProblemSet(List<Problem> problems);
    
    public abstract void addQualificationSet(List<Qualification> qualifications);
    
    
    
    
    
}
