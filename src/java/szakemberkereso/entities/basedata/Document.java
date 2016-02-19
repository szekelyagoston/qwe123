/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.entities.basedata;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gusztafszon
 */
@Entity
@Table(name="Document")
@NamedQueries({
    @NamedQuery(name="Document.findById",query="SELECT d FROM Document d WHERE d.id = :id")
})
public class Document implements Serializable{
    
    @Column(name="id")
    @Id @GeneratedValue
    private Long id;
    
    @Column(name="byte_value")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] valueInByte;
    
    @Column(name="comment")
    private String comment;
    
    @Column(name="title")
    private String title;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="specialist_id")
    private Specialist specialist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getValueInByte() {
        return valueInByte;
    }

    public void setValueInByte(byte[] valueInByte) {
        this.valueInByte = valueInByte;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    
    
    
}
