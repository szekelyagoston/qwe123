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
@Table(name="Opening_hours_M")
@NamedQueries({
    @NamedQuery(name="ModifiedOpeningHours.findById",query="SELECT d FROM ModifiedOpeningHours d WHERE d.id = :id")
})
public class ModifiedOpeningHours extends BaseOpeningHours{
   
    @ManyToOne
    @JoinColumn(name="office_id")
    private ModifiedOffice modifiedoffice;

    public ModifiedOpeningHours() {
    }
    
    
    
    ModifiedOpeningHours(OpeningHoursDto openingHoursDto, ModifiedOffice office) {
        super(openingHoursDto);
        this.modifiedoffice = office;
    }

    public ModifiedOffice getOffice() {
        return modifiedoffice;
    }

    public void setOffice(ModifiedOffice office) {
        this.modifiedoffice = office;
    }



    
}
