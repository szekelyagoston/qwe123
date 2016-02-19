/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.basedata;

import java.util.List;
import szakemberkereso.entities.checkboxdatas.IBaseExpandebaleValueList;

/**
 *
 * @author gusztafszon
 */
public interface IEntityWithValueList<T> {
    //public void setValueList(List<T> valueList);
    public void addValueListSet(List<T> valueList);
    public void clearValueListWithType(Class<?> clazz);
}
