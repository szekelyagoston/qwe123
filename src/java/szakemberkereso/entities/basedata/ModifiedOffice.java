/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.basedata;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.specialists.dto.OpeningHoursDto;

/**
 *
 * @author gusztafszon
 */
@Entity
@Table(name="Modified_Office")
public class ModifiedOffice extends BaseOffice{
    
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name="office_id")
    private Office office;
    
    @OneToMany(mappedBy = "modifiedoffice",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModifiedOpeningHours> modifiedOpeningHours;

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
    

    @Override
    public List<? extends BaseOpeningHours> getOpeningHours() {
        return modifiedOpeningHours;
    }

    @Override
    public void setOpeningHours(List<? extends BaseOpeningHours> openingHours) {
        this.modifiedOpeningHours = (List<ModifiedOpeningHours>)openingHours;
    }
    
    @Override
    public void addOpeningHour(BaseOpeningHours openingHours){
        this.modifiedOpeningHours.add((ModifiedOpeningHours)openingHours);
    }

    @Override
    public void addOpeningHourSet(List<? extends BaseOpeningHours> oldOpeningHour) {
        this.modifiedOpeningHours.addAll((List<ModifiedOpeningHours>)oldOpeningHour);
    }
    
    @Override
    public void addOpeningHourFromDto(OpeningHoursDto openingHoursDto) {
        this.modifiedOpeningHours.add(new ModifiedOpeningHours(openingHoursDto, this));
    }
    
}
