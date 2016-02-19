/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gusztafszon
 */
public class SpecialistWithOfficesDto extends SpecialistDto{
    
    protected List<OfficeDto> officeList;
    
    public SpecialistWithOfficesDto() {
        officeList = new ArrayList<>();
    }
    
    public List<OfficeDto> getOfficeList() {
        return officeList;
    }

    public void setOfficeList(List<OfficeDto> officeList) {
        this.officeList = officeList;
    }

   
    
    
    
}
