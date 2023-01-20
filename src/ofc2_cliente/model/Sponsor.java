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


/**
 * Entity for Sponsor
 * @author Elias
 */
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

    public Sponsor(Long id, String name, Integer phone, String email, String date, 
            Boolean status, List<Event> events, Admin admin, AdType ad) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleIntegerProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.date = new SimpleStringProperty(date);
        this.status = new SimpleBooleanProperty(status);
        this.events = new SimpleListProperty<>((ObservableList<Event>) events);
        this.admin = new SimpleObjectProperty(admin);
        this.ad = new  SimpleObjectProperty(ad);
    }

    public SimpleLongProperty getId() {
        return id;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleIntegerProperty getPhone() {
        return phone;
    }

    public SimpleStringProperty getEmail() {
        return email;
    }

    public SimpleStringProperty getDate() {
        return date;
    }

    public SimpleBooleanProperty getStatus() {
        return status;
    }

    public SimpleListProperty<Event> getEvents() {
        return events;
    }

    public SimpleObjectProperty<Admin> getAdmin() {
        return admin;
    }

    public SimpleObjectProperty<AdType> getAd() {
        return ad;
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
