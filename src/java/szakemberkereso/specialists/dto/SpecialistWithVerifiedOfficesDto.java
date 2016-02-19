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
public class SpecialistWithVerifiedOfficesDto extends SpecialistWithOfficesDto{

    public SpecialistWithVerifiedOfficesDto() {
        super();
    }
    
    
    
    @Override
    public void setOfficeList(List<OfficeDto> officeList) {
        for (OfficeDto officeDto : officeList){
            if (officeDto.getVerified()){
                this.officeList.add(officeDto);
            }
        }
    }

    @Override
    public void setProblems(List<ProblemDto> problems) {
        for (ProblemDto problemDto : problems){
            if(problemDto.getVerified()){
                this.problems.add(problemDto);
            }
        }
    }
    
    
}
