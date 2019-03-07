package dz.talcorp.ae.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Examen entity.
 */
public class ExamenDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateExamen;


    private Long voitureId;

    private String voitureMarque;

    private Long examinateurId;

    private String examinateurNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateExamen() {
        return dateExamen;
    }

    public void setDateExamen(LocalDate dateExamen) {
        this.dateExamen = dateExamen;
    }

    public Long getVoitureId() {
        return voitureId;
    }

    public void setVoitureId(Long carId) {
        this.voitureId = carId;
    }

    public String getVoitureMarque() {
        return voitureMarque;
    }

    public void setVoitureMarque(String carMarque) {
        this.voitureMarque = carMarque;
    }

    public Long getExaminateurId() {
        return examinateurId;
    }

    public void setExaminateurId(Long examinateurId) {
        this.examinateurId = examinateurId;
    }

    public String getExaminateurNom() {
        return examinateurNom;
    }

    public void setExaminateurNom(String examinateurNom) {
        this.examinateurNom = examinateurNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExamenDTO examenDTO = (ExamenDTO) o;
        if (examenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamenDTO{" +
            "id=" + getId() +
            ", dateExamen='" + getDateExamen() + "'" +
            ", voiture=" + getVoitureId() +
            ", voiture='" + getVoitureMarque() + "'" +
            ", examinateur=" + getExaminateurId() +
            ", examinateur='" + getExaminateurNom() + "'" +
            "}";
    }
}
