package dz.talcorp.ae.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Virement entity.
 */
public class VirementDTO implements Serializable {

    private Long id;

    @NotNull
    private Double montant;

    @NotNull
    private Instant dateVirement;


    private Long candidatId;

    private String candidatNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Instant getDateVirement() {
        return dateVirement;
    }

    public void setDateVirement(Instant dateVirement) {
        this.dateVirement = dateVirement;
    }

    public Long getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(Long candidatId) {
        this.candidatId = candidatId;
    }

    public String getCandidatNom() {
        return candidatNom;
    }

    public void setCandidatNom(String candidatNom) {
        this.candidatNom = candidatNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VirementDTO virementDTO = (VirementDTO) o;
        if (virementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), virementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VirementDTO{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", dateVirement='" + getDateVirement() + "'" +
            ", candidat=" + getCandidatId() +
            ", candidat='" + getCandidatNom() + "'" +
            "}";
    }
}
