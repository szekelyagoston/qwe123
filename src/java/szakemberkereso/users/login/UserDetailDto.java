/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.users.login;

import szakemberkereso.enums.SecGroup;

/**
 *
 * @author gusztafszon
 */
public class UserDetailDto {
    private final String email;
    private final String secGroup;

    public UserDetailDto(String email, String role) {
        this.email = email;
        
        this.secGroup = role;
        
        
    }
    
     

    public String getEmail() {
        return email;
    }

    public String getSecGroup() {
        return secGroup;
    }
    
    

    
    
    
}
