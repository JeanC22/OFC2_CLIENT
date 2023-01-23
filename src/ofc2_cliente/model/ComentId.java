/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Jeanpierr Caraballo
 */
@Embeddable
public class ComentId implements Serializable {

    private Long event_id;
    private Long client_id;

    public ComentId() {

    }

    @XmlElement(name = "event_id")

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    @XmlElement(name = "client_id")

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.event_id);
        hash = 61 * hash + Objects.hashCode(this.client_id);
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
        final ComentId other = (ComentId) obj;
        if (!Objects.equals(this.event_id, other.event_id)) {
            return false;
        }
        if (!Objects.equals(this.client_id, other.client_id)) {
            return false;
        }
        return true;
    }

}
