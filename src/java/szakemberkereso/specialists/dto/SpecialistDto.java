/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gusztafszon
 */
public class SpecialistDto extends NotModifiableSpecialistDto{
    
    
    private NotModifiableSpecialistDto modifiedSpecialist;
    
    

    public SpecialistDto() {
        super();
    }

    public NotModifiableSpecialistDto getModifiedSpecialist() {
        return modifiedSpecialist;
    }

    public void setModifiedSpecialist(NotModifiableSpecialistDto modifiedSpecialist) {
        this.modifiedSpecialist = modifiedSpecialist;
    }
    
    

    
    

   
    
    
    
    
}
