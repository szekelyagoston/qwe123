/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.basedata;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import szakemberkereso.enums.Day;
import szakemberkereso.specialists.dto.OpeningHoursDto;

/**
 *
 * @author gusztafszon
 */
@Entity
@Table(name="Opening_hours")
@NamedQueries({
    @NamedQuery(name="OpeningHours.findById",query="SELECT d FROM OpeningHours d WHERE d.id = :id")
})
public class OpeningHours extends BaseOpeningHours{
   
    
    @ManyToOne
    @JoinColumn(name="office_id")
    private Office office;

    public OpeningHours() {
    }

    
    
    OpeningHours(OpeningHoursDto openingHoursDto) {
        super(openingHoursDto);
    }

    OpeningHours(OpeningHoursDto openingHoursDto, Office office) {
        super(openingHoursDto);
        this.office = office;
    }
    
    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }



    
    
    
}
