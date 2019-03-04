package dz.talcorp.ae.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false, unique = true)
    private String matricule;

    @Column(name = "marque")
    private String marque;

    @OneToMany(mappedBy = "voiture")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Examen> examen = new HashSet<>();
    @OneToMany(mappedBy = "voiture")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lesson> lessons = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public Car matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMarque() {
        return marque;
    }

    public Car marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Set<Examen> getExamen() {
        return examen;
    }

    public Car examen(Set<Examen> examen) {
        this.examen = examen;
        return this;
    }

    public Car addExamen(Examen examen) {
        this.examen.add(examen);
        examen.setVoiture(this);
        return this;
    }

    public Car removeExamen(Examen examen) {
        this.examen.remove(examen);
        examen.setVoiture(null);
        return this;
    }

    public void setExamen(Set<Examen> examen) {
        this.examen = examen;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public Car lessons(Set<Lesson> lessons) {
        this.lessons = lessons;
        return this;
    }

    public Car addLesson(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.setVoiture(this);
        return this;
    }

    public Car removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.setVoiture(null);
        return this;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        if (car.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", marque='" + getMarque() + "'" +
            "}";
    }
}
