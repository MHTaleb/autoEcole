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
    private String nomEcole;

    @NotNull
    private String president;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEcole() {
        return nomEcole;
    }

    public void setNomEcole(String nomEcole) {
        this.nomEcole = nomEcole;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
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
            ", nomEcole='" + getNomEcole() + "'" +
            ", president='" + getPresident() + "'" +
            "}";
    }
}
