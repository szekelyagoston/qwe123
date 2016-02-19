/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dto;

import java.util.List;

/**
 *
 * @author gusztafszon
 */
public class ProblemWithConnectedSymptomsDto extends ProblemDto{
    private List<SymptomDto> connectedSymptom;

    public List<SymptomDto> getConnectedSymptom() {
        return connectedSymptom;
    }

    public void setConnectedSymptom(List<SymptomDto> connectedSymptom) {
        this.connectedSymptom = connectedSymptom;
    }
        
        
}
