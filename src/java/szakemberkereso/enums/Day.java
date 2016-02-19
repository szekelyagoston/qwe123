/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.enums;

/**
 *
 * @author gusztafszon
 */
public enum Day {
    MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6), SUNDAY(7);
    
    private final int sortNumber;

    Day(int sortNumber) {
        this.sortNumber = sortNumber;
    }

    public int getSortNumber() {
        return sortNumber;
    }

   
}
