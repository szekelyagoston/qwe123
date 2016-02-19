/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.checkboxdatas;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gusztafszon
 */
@Entity
@Table(name="qualification")
@NamedQueries({
    @NamedQuery(name="Qualification.findById",query="SELECT c FROM Qualification c WHERE c.id = :id"),
    @NamedQuery(name="Qualification.findByStringValue",query="SELECT c FROM Qualification c WHERE c.stringValue = :stringValue"),
    @NamedQuery(name="Qualification.getAll",query="SELECT c FROM Qualification c WHERE c.verified = FALSE"),
    @NamedQuery(name="Qualification.getAllVerified",query="SELECT c FROM Qualification c WHERE c.verified = TRUE")
})
public class Qualification extends BaseCheckboxData implements IBaseExpandebaleValueList{

    @Override
    public void setValue(String value) {
        this.setStringValue(value);
    }
    
}
