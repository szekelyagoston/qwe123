/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.basedata;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author gusztafszon
 */
@MappedSuperclass
public abstract class Verifiable implements Serializable{
    
    @Column(name = "verified")
    private Boolean verified;
    
    @Column(name = "denied")
    private Boolean denied;
    
    @Column(name="denied_message")
    private String deniedMessage;

    public Verifiable() {
        this.verified = false;
        this.denied = false;
    }
    
    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getDenied() {
        return denied;
    }

    public void setDenied(Boolean denied) {
        this.denied = denied;
    }

    public String getDeniedMessage() {
        return deniedMessage;
    }

    public void setDeniedMessage(String deniedMessage) {
        this.deniedMessage = deniedMessage;
    }
    
    
    
}
