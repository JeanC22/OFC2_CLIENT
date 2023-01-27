/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.model;



import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author 2dam
 */

public class Client extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Coment> comentarios;
    
    private List<Routine> rutinas;

    private List<Event> events;


    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }    
    
    @XmlTransient
    public List<Coment> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Coment> comentarios) {
        this.comentarios = comentarios;
    }

    @XmlTransient
    public List<Routine> getRutinas() {
        return rutinas;
    }

    public void setRutinas(List<Routine> rutinas) {
        this.rutinas = rutinas;
    }

  
    
}
