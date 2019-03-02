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

/**
 * A Virement.
 */
@Entity
@Table(name = "virement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "virement")
public class Virement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @NotNull
    @Column(name = "date_virement", nullable = false)
    private LocalDate dateVirement;

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

    public Double getMontant() {
        return montant;
    }

    public Virement montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDate getDateVirement() {
        return dateVirement;
    }

    public Virement dateVirement(LocalDate dateVirement) {
        this.dateVirement = dateVirement;
        return this;
    }

    public void setDateVirement(LocalDate dateVirement) {
        this.dateVirement = dateVirement;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Virement candidat(Candidat candidat) {
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
        Virement virement = (Virement) o;
        if (virement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), virement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Virement{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", dateVirement='" + getDateVirement() + "'" +
            "}";
    }
}
