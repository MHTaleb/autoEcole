package dz.talcorp.ae.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Car entity.
 */
public class CarDTO implements Serializable {

    private Long id;

    @NotNull
    private String matricule;

    private String marque;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarDTO carDTO = (CarDTO) o;
        if (carDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarDTO{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", marque='" + getMarque() + "'" +
            "}";
    }
}
