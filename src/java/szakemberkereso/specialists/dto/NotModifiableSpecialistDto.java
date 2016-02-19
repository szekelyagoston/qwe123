/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dto;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author gusztafszon
 */

public class NotModifiableSpecialistDto {
    private String firstName;
        
    private String lastName;

    private String picture;

    private String phoneNumber;
    
    //loaded from onetoone relation to user entity
    private String email;
    
    private List<DocumentDto> documentList;
    
    protected List<ProblemDto> problems;
    
    protected List<QualificationDto> qualifications;
    
    private Boolean denied;
    
    private Boolean verified;
    
    private String deniedMessage;
    
    private Boolean firstModificationDone;
    
    private Boolean modified;
    
    private String about;
    

    public NotModifiableSpecialistDto() {
        this.problems = new ArrayList<>();
        this.qualifications = new ArrayList<>();
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<DocumentDto> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<DocumentDto> documentList) {
        this.documentList = documentList;
    }

    public List<ProblemDto> getProblems() {
        return problems;
    }

    public void setProblems(List<ProblemDto> problems) {
        this.problems = problems;
    }

    public Boolean getDenied() {
        return denied;
    }

    public void setDenied(Boolean denied) {
        this.denied = denied;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getDeniedMessage() {
        return deniedMessage;
    }

    public void setDeniedMessage(String deniedMessage) {
        this.deniedMessage = deniedMessage;
    }

    public Boolean getFirstModificationDone() {
        return firstModificationDone;
    }

    public void setFirstModificationDone(Boolean firstModificationDone) {
        this.firstModificationDone = firstModificationDone;
    }


    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<QualificationDto> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<QualificationDto> qualifications) {
        this.qualifications = qualifications;
    }
    
    
    
    
    
    
    
}
