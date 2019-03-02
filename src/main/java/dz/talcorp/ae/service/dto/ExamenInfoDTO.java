package dz.talcorp.ae.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import dz.talcorp.ae.domain.enumeration.EtatExamen;
import dz.talcorp.ae.domain.enumeration.TypeExamen;

/**
 * A DTO for the ExamenInfo entity.
 */
public class ExamenInfoDTO implements Serializable {

    private Long id;

    private EtatExamen etat;

    private TypeExamen type;


    private Long examenId;

    private Long candidatId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtatExamen getEtat() {
        return etat;
    }

    public void setEtat(EtatExamen etat) {
        this.etat = etat;
    }

    public TypeExamen getType() {
        return type;
    }

    public void setType(TypeExamen type) {
        this.type = type;
    }

    public Long getExamenId() {
        return examenId;
    }

    public void setExamenId(Long examenId) {
        this.examenId = examenId;
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

        ExamenInfoDTO examenInfoDTO = (ExamenInfoDTO) o;
        if (examenInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examenInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamenInfoDTO{" +
            "id=" + getId() +
            ", etat='" + getEtat() + "'" +
            ", type='" + getType() + "'" +
            ", examen=" + getExamenId() +
            ", candidat=" + getCandidatId() +
            "}";
    }
}
