package dz.talcorp.ae.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import dz.talcorp.ae.domain.enumeration.TypeLesson;

/**
 * A Lesson.
 */
@Entity
@Table(name = "lesson")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "lesson")
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
    private LocalDate dateLesson;

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

    public LocalDate getDateLesson() {
        return dateLesson;
    }

    public Lesson dateLesson(LocalDate dateLesson) {
        this.dateLesson = dateLesson;
        return this;
    }

    public void setDateLesson(LocalDate dateLesson) {
        this.dateLesson = dateLesson;
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
            "}";
    }
}
