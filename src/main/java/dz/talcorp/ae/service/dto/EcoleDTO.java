package dz.talcorp.ae.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Ecole entity.
 */
public class EcoleDTO implements Serializable {

    private Long id;

    @NotNull
    private String titreEcole;


    private Long presidentId;

    private String presidentNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreEcole() {
        return titreEcole;
    }

    public void setTitreEcole(String titreEcole) {
        this.titreEcole = titreEcole;
    }

    public Long getPresidentId() {
        return presidentId;
    }

    public void setPresidentId(Long entraineurId) {
        this.presidentId = entraineurId;
    }

    public String getPresidentNom() {
        return presidentNom;
    }

    public void setPresidentNom(String entraineurNom) {
        this.presidentNom = entraineurNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EcoleDTO ecoleDTO = (EcoleDTO) o;
        if (ecoleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ecoleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EcoleDTO{" +
            "id=" + getId() +
            ", titreEcole='" + getTitreEcole() + "'" +
            ", president=" + getPresidentId() +
            ", president='" + getPresidentNom() + "'" +
            "}";
    }
}
