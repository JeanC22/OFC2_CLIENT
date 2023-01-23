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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Entity for Sponsor
 * @author Elias
 */
@XmlRootElement(name="sponsor")
public class Sponsor implements Serializable {

    private final SimpleLongProperty id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty phone;
    private final SimpleStringProperty email;
    private final SimpleStringProperty date;
    private final SimpleBooleanProperty status;
    private final SimpleListProperty<Event> events;
    private final SimpleObjectProperty<Admin> admin;
    private final SimpleObjectProperty<AdType> ad;
    
     public Sponsor() {
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.phone = new SimpleIntegerProperty();
        this.email = new SimpleStringProperty();
        this.date = new SimpleStringProperty();
        this.status = new SimpleBooleanProperty();
        this.events = new SimpleListProperty<>();
        this.admin = new SimpleObjectProperty();
        this.ad = new  SimpleObjectProperty();
    }

    
    @XmlElement(name="name")
    public String getName(){
        return this.name.get();
    }
    
    public void setName(String name){
        this.name.set(name);
    }
    @XmlElement(name="phone")
    public Integer getPhone(){
        return this.phone.get();
    }
    
    public void setPhone(Integer phone){
        this.phone.set(phone);
    }
    @XmlElement(name="email")
    public String getEmail(){
        return this.name.get();
    }
    
    public void setEmail(String email){
        this.email.set(email);
    }
    @XmlElement(name="status")
    public Boolean getStatus(){
        return this.status.get();
    }
    
    public void setStatus(Boolean status){
        this.status.set(status);
    }
    @XmlElement(name="date")
    public String getDate() {
        return this.date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }
    
     public List<Event> getEvents() {
        return this.events.get();
    }
    
    public Admin getAdmin() {
        return this.admin.get();
    }

    public void setAdmin(Admin admin) {
        this.admin.set(admin);
    }
    @XmlElement(name="ad")
    public AdType getAd() {
        return this.ad.get();
    }

    public void setAd(AdType ad) {
        this.ad.set(ad);
    }

    public SimpleLongProperty getId() {
        return id;
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
