/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aritz
 */
@XmlRootElement(name="routine")
public class Routine implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Long id;

    
    private String name;

   
    private Date start_date;

    
    private Date end_date;
    
    private Double kcal;
    
   
    private Client clie;

    private List<Exercise> exercises;
    
    private Float routineTime;

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     @XmlElement(name="clie")
    public Client getClie() {
        return clie;
    }

    public void setClie(Client clie) {
        this.clie = clie;
    }

     @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     @XmlElement(name="start_date")
    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    @XmlElement(name="end_date")
    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

     @XmlElement(name="kcal")
    public Double getKcal() {
        return kcal;
    }

    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    @XmlElement(name="exercises")
    public List<Exercise> getEjercicios() {
        return exercises;
    }

    public void setEjercicios(List<Exercise> exercises) {
        this.exercises = exercises;
    }
    @XmlElement(name="routineTime")
    public Float getTime() {
        return routineTime;
    }

    public void setTime(Float time) {
        this.routineTime = time;
    }


    public Routine() {
        super();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Routine)) {
            return false;
        }
        Routine other = (Routine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User1[ id=" + id + " ]";
    }
}
