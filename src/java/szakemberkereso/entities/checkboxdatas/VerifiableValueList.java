/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.checkboxdatas;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author gusztafszon
 */
@MappedSuperclass
public abstract class VerifiableValueList implements Serializable{
    
    @Column(name = "verified")
    private Boolean verified;

    public VerifiableValueList() {
        this.verified = false;
    }
    
    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
    
    
}
