/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.basedata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.specialists.dto.OpeningHoursDto;

/**
 *
 * @author gusztafszon
 */
@Entity
@Table(name="Office")
@NamedQueries({
    @NamedQuery(name="Office.findById",query="SELECT c FROM Office c WHERE c.id = :id"),
    @NamedQuery(name="Office.getAll",query="SELECT c FROM Office c"),
    @NamedQuery(name="Office.getAllVerified",query="SELECT c FROM Office c WHERE c.verified = TRUE"),
    @NamedQuery(name="Office.getAllNotVerified",query="SELECT c FROM Office c WHERE c.verified = FALSE"),
    @NamedQuery(name="Office.getAllNotVerifiedOrModified",query="SELECT c FROM Office c WHERE c.verified = FALSE OR c.modified = TRUE")
})
public class Office extends BaseOffice{
    
    @Column(name="modified")
    private Boolean modified;
    
    @OneToOne(optional = true, mappedBy = "office", cascade = CascadeType.ALL)
    private ModifiedOffice modifiedOffice;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="specialist_id")
    private Specialist specialist;
    

    @OneToMany(mappedBy = "office",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpeningHours> openingHours;


    public Office() {
        super();
        this.modifiedOffice = new ModifiedOffice();
        this.modified = false;
    }

    
    
    public Office(Specialist specialist) {
        super();
        this.specialist = specialist;
        this.modifiedOffice = new ModifiedOffice();
        this.modified = false;
    }
    
    public Office(String address) {
        super(address);
        this.modifiedOffice = new ModifiedOffice();
        this.modified = false;
    }


    

    @XmlTransient
    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    public ModifiedOffice getModifiedOffice() {
        return modifiedOffice;
    }

    public void setModifiedOffice(ModifiedOffice modifiedOffice) {
        this.modifiedOffice = modifiedOffice;
    }
    
    

    @Override
    public List<? extends BaseOpeningHours> getOpeningHours() {
        return openingHours;
    }

    @Override
    public void setOpeningHours(List<? extends BaseOpeningHours> openingHours) {
        this.openingHours = (List<OpeningHours>)openingHours;
    }
    
    @Override
    public void addOpeningHour(BaseOpeningHours openingHours){
        this.openingHours.add((OpeningHours)openingHours);
    }

    @Override
    public void addOpeningHourSet(List<? extends BaseOpeningHours> oldOpeningHour) {
        this.openingHours.addAll((List<OpeningHours>)oldOpeningHour);
    }

    @Override
    public void addOpeningHourFromDto(OpeningHoursDto openingHoursDto) {
        this.openingHours.add(new OpeningHours(openingHoursDto, this));
    }
    
    

    
    
    
    
}
