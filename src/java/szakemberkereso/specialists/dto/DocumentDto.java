/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dto;

/**
 *
 * @author gusztafszon
 */
public class DocumentDto {

    private Long id;
    
    private String title;
    
    //private byte[] valueInByte;
    
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public byte[] getValueInByte() {
//        return valueInByte;
//    }
//
//    public void setValueInByte(byte[] valueInByte) {
//        this.valueInByte = valueInByte;
//    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    

}
