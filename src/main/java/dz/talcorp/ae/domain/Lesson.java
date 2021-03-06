package dz.talcorp.ae.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import dz.talcorp.ae.domain.enumeration.TypeLesson;

import dz.talcorp.ae.domain.enumeration.EtatLesson;

/**
 * A Lesson.
 */
@Entity
@Table(name = "lesson")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_lesson", nullable = false)
    private TypeLesson typeLesson;

    @NotNull
    @Column(name = "date_lesson", nullable = false)
    private Instant dateLesson;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat_lesson", nullable = false)
    private EtatLesson etatLesson;

    @Lob
    @Column(name = "observation")
    private String observation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("candidats")
    private Candidat candidat;

    @ManyToOne
    @JsonIgnoreProperties("cars")
    private Car voiture;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("entraineurs")
    private Entraineur entraineur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeLesson getTypeLesson() {
        return typeLesson;
    }

    public Lesson typeLesson(TypeLesson typeLesson) {
        this.typeLesson = typeLesson;
        return this;
    }

    public void setTypeLesson(TypeLesson typeLesson) {
        this.typeLesson = typeLesson;
    }

    public Instant getDateLesson() {
        return dateLesson;
    }

    public Lesson dateLesson(Instant dateLesson) {
        this.dateLesson = dateLesson;
        return this;
    }

    public void setDateLesson(Instant dateLesson) {
        this.dateLesson = dateLesson;
    }

    public EtatLesson getEtatLesson() {
        return etatLesson;
    }

    public Lesson etatLesson(EtatLesson etatLesson) {
        this.etatLesson = etatLesson;
        return this;
    }

    public void setEtatLesson(EtatLesson etatLesson) {
        this.etatLesson = etatLesson;
    }

    public String getObservation() {
        return observation;
    }

    public Lesson observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Lesson candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Car getVoiture() {
        return voiture;
    }

    public Lesson voiture(Car car) {
        this.voiture = car;
        return this;
    }

    public void setVoiture(Car car) {
        this.voiture = car;
    }

    public Entraineur getEntraineur() {
        return entraineur;
    }

    public Lesson entraineur(Entraineur entraineur) {
        this.entraineur = entraineur;
        return this;
    }

    public void setEntraineur(Entraineur entraineur) {
        this.entraineur = entraineur;
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
        Lesson lesson = (Lesson) o;
        if (lesson.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lesson.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lesson{" +
            "id=" + getId() +
            ", typeLesson='" + getTypeLesson() + "'" +
            ", dateLesson='" + getDateLesson() + "'" +
            ", etatLesson='" + getEtatLesson() + "'" +
            ", observation='" + getObservation() + "'" +
            "}";
    }
}
