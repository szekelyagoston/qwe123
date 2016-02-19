/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dto;

/**
 *
 * @author gusztafszon
 */
public class OfficeWithSpecialistDto extends OfficeDto{
    private SpecialistDto specialist;

    public SpecialistDto getSpecialist() {
        return specialist;
    }

    public void setSpecialist(SpecialistDto specialist) {
        this.specialist = specialist;
    }

    
   
    
    
}
