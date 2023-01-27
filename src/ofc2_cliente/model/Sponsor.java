/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.model;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Entity for Sponsor
 * @author Elias
 */
@XmlRootElement(name="sponsor")
public class Sponsor implements Serializable {

    private Long id;

    private String name;

    private Integer phone;

    private String email;
    private Date date;

    private Boolean status;
    
    private List<Event> events;
    
    private Admin admin;

    private AdType ad;
    
    public Sponsor() {
        
    }

    public Sponsor( String name, Integer phone, String email,
            Date date,
            Boolean status,  AdType ad) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.date = date;
        this.status = status;
        this.ad = ad;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlElement(name = "phone")
    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
    @XmlElement(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @XmlElement(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @XmlElement(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    @XmlElement(name = "ad")
    public AdType getAd() {
        return ad;
    }

    public void setAd(AdType ad) {
        this.ad = ad;
    }
     
     
    


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.phone);
        hash = 79 * hash + Objects.hashCode(this.email);
        hash = 79 * hash + Objects.hashCode(this.date);
        hash = 79 * hash + Objects.hashCode(this.status);
        hash = 79 * hash + Objects.hashCode(this.events);
        hash = 79 * hash + Objects.hashCode(this.admin);
        hash = 79 * hash + Objects.hashCode(this.ad);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sponsor other = (Sponsor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.events, other.events)) {
            return false;
        }
        if (!Objects.equals(this.admin, other.admin)) {
            return false;
        }
        if (!Objects.equals(this.ad, other.ad)) {
            return false;
        }
        return true;
    }

    
    
    
}
