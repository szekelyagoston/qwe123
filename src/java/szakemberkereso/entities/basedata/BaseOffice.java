/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.basedata;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import szakemberkereso.entities.checkboxdatas.Problem;
import szakemberkereso.specialists.dto.OpeningHoursDto;

/**
 *
 * @author gusztafszon
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class BaseOffice extends Verifiable{
    @Column(name="id")
    @Id @GeneratedValue
    private Long id;
    
    @Column(name="address")
    private String address;
    
    @Column(name="user_give_address")
    private String userGivenAddress;
    
    @Column(name="lat")
    private Float lat;
    
    @Column(name="lng")
    private Float lng;
    
    

    public BaseOffice() {
        super();
    }

    public BaseOffice(String address) {
        super();
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserGivenAddress() {
        return userGivenAddress;
    }

    public void setUserGivenAddress(String userGivenAddress) {
        this.userGivenAddress = userGivenAddress;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }
    
    
    
    public abstract List<? extends BaseOpeningHours> getOpeningHours();

    public abstract void setOpeningHours(List<? extends BaseOpeningHours> problems);
    
    public abstract void addOpeningHour(BaseOpeningHours problem);
    
    public abstract void addOpeningHourSet(List<? extends BaseOpeningHours> problems);
    
    public abstract void addOpeningHourFromDto(OpeningHoursDto openingHoursDto);
}
