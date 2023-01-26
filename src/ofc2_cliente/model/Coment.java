/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.model;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing comments for users. It contains the following fields:
 * Long id, Date publication_date, Date modification_date, String message,
 * Integer valoration,Boolean privacity, String subject, Client comCli and Event
 * event
 *
 * @author Jeanpierr Caraballo
 */
@XmlRootElement(name = "coments")
public class Coment implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * compuesta por client_id y event_id
     */
    private ComentId comentid;

    private SimpleObjectProperty<Date> publication_date;

    private SimpleObjectProperty<Date> modification_date;

    private SimpleStringProperty message;

    private SimpleStringProperty valoration;

    private SimpleStringProperty privacity;

    private SimpleStringProperty subject;

    private Client comClie;

    private Event event;

    String pattern = "dd/MM/yyyy";

    public Coment() {
        this.publication_date = new SimpleObjectProperty();
        this.modification_date = new SimpleObjectProperty();
        this.message = new SimpleStringProperty();
        this.valoration = new SimpleStringProperty();
        this.privacity = new SimpleStringProperty();
        this.subject = new SimpleStringProperty();
        this.comClie = new Client();
        this.event = new Event();
    }

    public Coment(Client userId, Event eventID, Date publication_date,
            Date modification_date, String message,
            String valoration, String privacity, String subject) {
        this.publication_date = new SimpleObjectProperty(publication_date);
        this.modification_date = new SimpleObjectProperty(modification_date);
        this.message = new SimpleStringProperty(message);
        this.valoration = new SimpleStringProperty(valoration);
        this.privacity = new SimpleStringProperty(privacity);
        this.subject = new SimpleStringProperty(subject);

    }

    public Coment(Date date,
            String message,
            String valoration, String privacity, String subject) {
        this.publication_date = new SimpleObjectProperty(date);
        this.message = new SimpleStringProperty(message);
        this.valoration = new SimpleStringProperty(valoration);
        this.privacity = new SimpleStringProperty(privacity);
        this.subject = new SimpleStringProperty(subject);
    }

    @XmlElement(name = "comentid")
    public ComentId getComentid() {
        return comentid;
    }

    public void setComentid(ComentId comentid) {
        this.comentid = comentid;
    }

    @XmlElement(name = "publication_date")
    public Date getPublication_date() throws ParseException {
        return this.publication_date.get();

    }

    public void setPublication_date(Date publication_date) {
        this.publication_date.set(publication_date);
    }

    @XmlElement(name = "modification_date")

    public Date getModification_date() {
        return modification_date.get();
    }

    public void setModification_date(Date modification_date) {
        this.modification_date.set(modification_date);
    }

    @XmlElement(name = "message")

    public String getMessage() {
        return message.get();
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    @XmlElement(name = "valoration")

    public String getValoration() {
        return this.valoration.get();
    }

    public void setValoration(String valoration) {
        this.valoration.set(valoration);
    }

    @XmlElement(name = "privacity")

    public String getPrivacity() {
        return privacity.get();
    }

    public void setPrivacity(String privacity) {
        this.privacity.set(privacity);
    }

    @XmlElement(name = "subject")

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public Client getComClie() {
        return comClie;
    }

    public void setComClie(Client client) {
        this.comClie = client;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Coment{" + "comentid=" + comentid + ", publication_date=" + publication_date + ", modification_date=" + modification_date + ", message=" + message + ", valoration=" + valoration + ", privacity=" + privacity + ", subject=" + subject + ", comClie=" + comClie + ", event=" + event + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.comentid);
        hash = 71 * hash + Objects.hashCode(this.publication_date);
        hash = 71 * hash + Objects.hashCode(this.modification_date);
        hash = 71 * hash + Objects.hashCode(this.message);
        hash = 71 * hash + Objects.hashCode(this.valoration);
        hash = 71 * hash + Objects.hashCode(this.privacity);
        hash = 71 * hash + Objects.hashCode(this.subject);
        hash = 71 * hash + Objects.hashCode(this.comClie);
        hash = 71 * hash + Objects.hashCode(this.event);
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
        final Coment other = (Coment) obj;
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        if (!Objects.equals(this.comentid, other.comentid)) {
            return false;
        }
        if (!Objects.equals(this.publication_date, other.publication_date)) {
            return false;
        }
        if (!Objects.equals(this.modification_date, other.modification_date)) {
            return false;
        }
        if (!Objects.equals(this.valoration, other.valoration)) {
            return false;
        }
        if (!Objects.equals(this.privacity, other.privacity)) {
            return false;
        }
        if (!Objects.equals(this.comClie, other.comClie)) {
            return false;
        }
        return Objects.equals(this.event, other.event);
    }

}
