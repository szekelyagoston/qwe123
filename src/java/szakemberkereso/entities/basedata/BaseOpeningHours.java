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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import szakemberkereso.enums.Day;
import szakemberkereso.specialists.dto.OpeningHoursDto;

/**
 *
 * @author gusztafszon
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class BaseOpeningHours implements Serializable{
    
    
    @Id @GeneratedValue
    private Long id;
    
    @Enumerated(EnumType.ORDINAL)
    private Day openingDay;
    
    //0-2400
    private Integer fromClock;
    
    private Integer toClock;

    public BaseOpeningHours() {
    }

    public BaseOpeningHours(OpeningHoursDto openingHoursDto) {
        this.fromClock = openingHoursDto.getFromClock();
        this.toClock = openingHoursDto.getToClock();
        this.openingDay = openingHoursDto.getOpeningDay();
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getOpeningDay() {
        return openingDay;
    }

    public void setOpeningDay(Day openingDay) {
        this.openingDay = openingDay;
    }

    public Integer getFromClock() {
        return fromClock;
    }

    public void setFromClock(Integer fromClock) {
        this.fromClock = fromClock;
    }

    public Integer getToClock() {
        return toClock;
    }

    public void setToClock(Integer toClock) {
        this.toClock = toClock;
    }
   
    
}
