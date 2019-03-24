package dz.talcorp.ae.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(name = "titre_ecole", nullable = false, unique = true)
    private String titreEcole;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("ecoles")
    private Entraineur president;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreEcole() {
        return titreEcole;
    }

    public Ecole titreEcole(String titreEcole) {
        this.titreEcole = titreEcole;
        return this;
    }

    public void setTitreEcole(String titreEcole) {
        this.titreEcole = titreEcole;
    }

    public Entraineur getPresident() {
        return president;
    }

    public Ecole president(Entraineur entraineur) {
        this.president = entraineur;
        return this;
    }

    public void setPresident(Entraineur entraineur) {
        this.president = entraineur;
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
            ", titreEcole='" + getTitreEcole() + "'" +
            "}";
    }
}
