/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.model;



import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author 2dam
 */

public class Admin extends User implements Serializable {

    private static final long serialVersionUID = 1L;
 
    private Set<Event> events;
 
    private Set<Sponsor> sponsors;

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    public Set<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Set<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }
    
    

}
