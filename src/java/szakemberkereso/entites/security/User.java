package szakemberkereso.entites.security;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.annotate.JsonIgnore;
import szakemberkereso.entities.basedata.Office;
import szakemberkereso.entities.basedata.Specialist;
import szakemberkereso.enums.SecGroup;

/**
 *
 * @author developer
 */
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name="User.findByEmail", query="SELECT u from User u WHERE u.email = :email"),
    @NamedQuery(name="User.findByActivationString", query="SELECT u from User u WHERE u.activationString = :activationString")
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    
    @Column(name="secGroup")
    private String secGroup;
    
    @Column(name="active")
    private Boolean active;
    
    @Column(name="activation_string", unique = true)
    private String activationString;
    
    @Column(name="registration_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationTimeStamp;
    
    @OneToOne(optional = false, mappedBy = "user", cascade = CascadeType.ALL)
    private Specialist specialist;

    public User() {
        specialist = new Specialist(this);
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecGroup() {
        return secGroup;
    }

    public void setSecGroup(String secGroup) {
        this.secGroup = secGroup;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getActivationString() {
        return activationString;
    }

    public void setActivationString(String activationString) {
        this.activationString = activationString;
    }

    public Date getRegistrationTimeStamp() {
        return registrationTimeStamp;
    }

    public void setRegistrationTimeStamp(Date registrationTimeStamp) {
        this.registrationTimeStamp = registrationTimeStamp;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }
    
    
    
    

    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

   
    
}
