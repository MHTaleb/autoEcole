package dz.talcorp.ae.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import dz.talcorp.ae.domain.enumeration.Nationalite;

/**
 * A DTO for the Candidat entity.
 */
public class CandidatDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] photo;

    private String photoContentType;
    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    private String pere;

    @NotNull
    private String mere;

    @NotNull
    @Pattern(regexp = "[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]")
    private String telephone;

    @NotNull
    private LocalDate dateInscription;

    @NotNull
    private LocalDate dateNaissance;

    @NotNull
    private String lieuNaissance;

    @NotNull
    private Nationalite nationalite;

    @NotNull
    private String adresse;


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

    public String getPere() {
        return pere;
    }

    public void setPere(String pere) {
        this.pere = pere;
    }

    public String getMere() {
        return mere;
    }

    public void setMere(String mere) {
        this.mere = mere;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public Nationalite getNationalite() {
        return nationalite;
    }

    public void setNationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CandidatDTO candidatDTO = (CandidatDTO) o;
        if (candidatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandidatDTO{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", pere='" + getPere() + "'" +
            ", mere='" + getMere() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", dateInscription='" + getDateInscription() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", nationalite='" + getNationalite() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
