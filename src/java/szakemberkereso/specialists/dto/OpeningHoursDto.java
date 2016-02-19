/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import szakemberkereso.enums.Day;

/**
 *
 * @author gusztafszon
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpeningHoursDto {


    //0-2400
    private Integer fromClock;
    
    private Integer toClock;
    
    private Day openingDay;
    
    private int sortNumber;


    
    

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

   
    public Day getOpeningDay() {
        return openingDay;
    }

    public void setOpeningDay(Day openingDay) {
        this.openingDay = openingDay;
        this.sortNumber = openingDay.getSortNumber();
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }
    
    
    
}
