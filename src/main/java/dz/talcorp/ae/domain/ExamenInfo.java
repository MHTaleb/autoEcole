package dz.talcorp.ae.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import dz.talcorp.ae.domain.enumeration.EtatExamen;

import dz.talcorp.ae.domain.enumeration.TypeExamen;

/**
 * A ExamenInfo.
 */
@Entity
@Table(name = "examen_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "exameninfo")
public class ExamenInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatExamen etat;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeExamen type;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("examen")
    private Examen examen;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("candidats")
    private Candidat candidat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtatExamen getEtat() {
        return etat;
    }

    public ExamenInfo etat(EtatExamen etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(EtatExamen etat) {
        this.etat = etat;
    }

    public TypeExamen getType() {
        return type;
    }

    public ExamenInfo type(TypeExamen type) {
        this.type = type;
        return this;
    }

    public void setType(TypeExamen type) {
        this.type = type;
    }

    public Examen getExamen() {
        return examen;
    }

    public ExamenInfo examen(Examen examen) {
        this.examen = examen;
        return this;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public ExamenInfo candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
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
        ExamenInfo examenInfo = (ExamenInfo) o;
        if (examenInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examenInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamenInfo{" +
            "id=" + getId() +
            ", etat='" + getEtat() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
