/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.helpers;

/**
 *
 * @author gusztafszon
 */
public class EntityFactory<T> {
    Class<T> clazz;

    public EntityFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getInstance() throws InstantiationException, IllegalAccessException{
        return clazz.newInstance();
    }
}
