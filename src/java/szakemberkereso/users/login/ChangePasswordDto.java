/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.users.login;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author gusztafszon
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordDto {
    private String oldpassword;
    private String newpassword;
    private String newpasswordagain;

    public ChangePasswordDto() {
    }
    
    

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getNewpasswordagain() {
        return newpasswordagain;
    }

    public void setNewpasswordagain(String newpasswordagain) {
        this.newpasswordagain = newpasswordagain;
    }
    
    
    
    
}
