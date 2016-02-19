/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import szakemberkereso.entities.checkboxdatas.Problem;


/**
 *
 * @author gusztafszon
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfficeDto extends NotModifiableOfficeDto{
    
    private NotModifiableOfficeDto modifiedOffice;

    public NotModifiableOfficeDto getModifiedOffice() {
        return modifiedOffice;
    }

    public void setModifiedOffice(NotModifiableOfficeDto modifiedOffice) {
        this.modifiedOffice = modifiedOffice;
    }
    

    
    
    
}
