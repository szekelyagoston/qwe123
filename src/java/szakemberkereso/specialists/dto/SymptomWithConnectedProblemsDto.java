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
public class SymptomWithConnectedProblemsDto extends SymptomDto{
    private List<ProblemDto> connectedProblem;

    public List<ProblemDto> getConnectedProblem() {
        return connectedProblem;
    }

    public void setConnectedProblem(List<ProblemDto> connectedProblem) {
        this.connectedProblem = connectedProblem;
    }
    
    
    
}
