package dz.talcorp.ae.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Ecole.
 */
@Entity
@Table(name = "ecole")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ecole implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom_ecole", nullable = false)
    private String nomEcole;

    @NotNull
    @Column(name = "president", nullable = false)
    private String president;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEcole() {
        return nomEcole;
    }

    public Ecole nomEcole(String nomEcole) {
        this.nomEcole = nomEcole;
        return this;
    }

    public void setNomEcole(String nomEcole) {
        this.nomEcole = nomEcole;
    }

    public String getPresident() {
        return president;
    }

    public Ecole president(String president) {
        this.president = president;
        return this;
    }

    public void setPresident(String president) {
        this.president = president;
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
        Ecole ecole = (Ecole) o;
        if (ecole.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ecole.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ecole{" +
            "id=" + getId() +
            ", nomEcole='" + getNomEcole() + "'" +
            ", president='" + getPresident() + "'" +
            "}";
    }
}
