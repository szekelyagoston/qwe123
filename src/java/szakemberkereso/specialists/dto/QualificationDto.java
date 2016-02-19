/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author gusztafszon
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QualificationDto extends VerifiableDto{
    
    private Long id;
    private String stringValue;

    public void setId(Long id) {
        this.id = id;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
    
    

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getValue() {
        return getStringValue();
    }
    
}
