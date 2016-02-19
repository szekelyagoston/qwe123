/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.search.dto;

import szakemberkereso.specialists.dto.SymptomDto;

/**
 *
 * @author gusztafszon
 */
public class SearchDto {
    
    private SymptomDto symptom;

    public SymptomDto getSymptom() {
        return symptom;
    }

    public void setSymptom(SymptomDto symptom) {
        this.symptom = symptom;
    }
    
    
}
