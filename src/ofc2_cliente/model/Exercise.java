/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aritz
 */
@Entity
@Table(name = "EXERCISES", schema = "OFC_DB")
@NamedQueries({
    @NamedQuery(name="consultExerciseByName", query="SELECT e FROM Exercise e WHERE e.exercise= :exercise"),
    
    @NamedQuery(name="consultAllExercises", query="SELECT e FROM Exercise e"),
    
    @NamedQuery(name= "consultExerciseById", query="SELECT e FROM Exercise e WHERE e.id= :id"),
})
@XmlRootElement
public class Exercise implements Serializable {

   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Float time;
    
    @ManyToMany(mappedBy= "exercises", fetch= FetchType.EAGER)
    private Set<Routine> routines;
    
    @Enumerated(EnumType.STRING)
    private Exercises exercise;

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTime() {
        return time;
    }

    public void setTime(Float time) {
        this.time = time;
    }

    @XmlTransient
    public Set<Routine> getRoutines() {
        return routines;
    }

    public void setRoutines(Set<Routine> routines) {
        this.routines = routines;
    }

    public Exercises getExercise() {
        return exercise;
    }

    public void setExercise(Exercises exercise) {
        this.exercise = exercise;
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
        if (!(object instanceof Exercise)) {
            return false;
        }
        Exercise other = (Exercise) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User1[ id=" + id + " ]";
    }
    
    
    public Exercise() {
        super();
    }

   
}
