package dz.talcorp.ae.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Examen.
 */
@Entity
@Table(name = "examen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "examen")
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_examen", nullable = false)
    private LocalDate dateExamen;

    @OneToMany(mappedBy = "examen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExamenInfo> examenInfos = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("cars")
    private Car voiture;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("examinateurs")
    private Examinateur examinateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateExamen() {
        return dateExamen;
    }

    public Examen dateExamen(LocalDate dateExamen) {
        this.dateExamen = dateExamen;
        return this;
    }

    public void setDateExamen(LocalDate dateExamen) {
        this.dateExamen = dateExamen;
    }

    public Set<ExamenInfo> getExamenInfos() {
        return examenInfos;
    }

    public Examen examenInfos(Set<ExamenInfo> examenInfos) {
        this.examenInfos = examenInfos;
        return this;
    }

    public Examen addExamenInfo(ExamenInfo examenInfo) {
        this.examenInfos.add(examenInfo);
        examenInfo.setExamen(this);
        return this;
    }

    public Examen removeExamenInfo(ExamenInfo examenInfo) {
        this.examenInfos.remove(examenInfo);
        examenInfo.setExamen(null);
        return this;
    }

    public void setExamenInfos(Set<ExamenInfo> examenInfos) {
        this.examenInfos = examenInfos;
    }

    public Car getVoiture() {
        return voiture;
    }

    public Examen voiture(Car car) {
        this.voiture = car;
        return this;
    }

    public void setVoiture(Car car) {
        this.voiture = car;
    }

    public Examinateur getExaminateur() {
        return examinateur;
    }

    public Examen examinateur(Examinateur examinateur) {
        this.examinateur = examinateur;
        return this;
    }

    public void setExaminateur(Examinateur examinateur) {
        this.examinateur = examinateur;
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
        Examen examen = (Examen) o;
        if (examen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Examen{" +
            "id=" + getId() +
            ", dateExamen='" + getDateExamen() + "'" +
            "}";
    }
}
