package dz.talcorp.ae.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Entraineur entity.
 */
public class EntraineurDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] photo;

    private String photoContentType;
    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    @Pattern(regexp = "[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]")
    private String telephone;


    private Long compteId;

    private String compteLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getCompteId() {
        return compteId;
    }

    public void setCompteId(Long userId) {
        this.compteId = userId;
    }

    public String getCompteLogin() {
        return compteLogin;
    }

    public void setCompteLogin(String userLogin) {
        this.compteLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntraineurDTO entraineurDTO = (EntraineurDTO) o;
        if (entraineurDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entraineurDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntraineurDTO{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", compte=" + getCompteId() +
            ", compte='" + getCompteLogin() + "'" +
            "}";
    }
}
