package dz.talcorp.ae.service.dto;
import java.time.LocalDate;
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
    private LocalDate dateVirement;


    private Long candidatId;

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

    public LocalDate getDateVirement() {
        return dateVirement;
    }

    public void setDateVirement(LocalDate dateVirement) {
        this.dateVirement = dateVirement;
    }

    public Long getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(Long candidatId) {
        this.candidatId = candidatId;
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
            "}";
    }
}
