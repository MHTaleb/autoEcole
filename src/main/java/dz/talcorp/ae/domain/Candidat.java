package dz.talcorp.ae.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import dz.talcorp.ae.domain.enumeration.Nationalite;

/**
 * A Candidat.
 */
@Entity
@Table(name = "candidat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "candidat")
public class Candidat implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "pere", nullable = false)
    private String pere;

    @NotNull
    @Column(name = "mere", nullable = false)
    private String mere;

    @NotNull
    @Pattern(regexp = "[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]")
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull
    @Column(name = "date_inscription", nullable = false)
    private LocalDate dateInscription;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @NotNull
    @Column(name = "lieu_naissance", nullable = false)
    private String lieuNaissance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nationalite", nullable = false)
    private Nationalite nationalite;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "nid", nullable = false, unique = true)
    private String nid;

    @OneToMany(mappedBy = "candidat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExamenInfo> examenInfos = new HashSet<>();
    @OneToMany(mappedBy = "candidat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Virement> virements = new HashSet<>();
    @OneToMany(mappedBy = "candidat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lesson> lessons = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Candidat photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Candidat photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getNom() {
        return nom;
    }

    public Candidat nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Candidat prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPere() {
        return pere;
    }

    public Candidat pere(String pere) {
        this.pere = pere;
        return this;
    }

    public void setPere(String pere) {
        this.pere = pere;
    }

    public String getMere() {
        return mere;
    }

    public Candidat mere(String mere) {
        this.mere = mere;
        return this;
    }

    public void setMere(String mere) {
        this.mere = mere;
    }

    public String getTelephone() {
        return telephone;
    }

    public Candidat telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public Candidat dateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
        return this;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Candidat dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public Candidat lieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public Nationalite getNationalite() {
        return nationalite;
    }

    public Candidat nationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
        return this;
    }

    public void setNationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
    }

    public String getAdresse() {
        return adresse;
    }

    public Candidat adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNid() {
        return nid;
    }

    public Candidat nid(String nid) {
        this.nid = nid;
        return this;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Set<ExamenInfo> getExamenInfos() {
        return examenInfos;
    }

    public Candidat examenInfos(Set<ExamenInfo> examenInfos) {
        this.examenInfos = examenInfos;
        return this;
    }

    public Candidat addExamenInfo(ExamenInfo examenInfo) {
        this.examenInfos.add(examenInfo);
        examenInfo.setCandidat(this);
        return this;
    }

    public Candidat removeExamenInfo(ExamenInfo examenInfo) {
        this.examenInfos.remove(examenInfo);
        examenInfo.setCandidat(null);
        return this;
    }

    public void setExamenInfos(Set<ExamenInfo> examenInfos) {
        this.examenInfos = examenInfos;
    }

    public Set<Virement> getVirements() {
        return virements;
    }

    public Candidat virements(Set<Virement> virements) {
        this.virements = virements;
        return this;
    }

    public Candidat addVirement(Virement virement) {
        this.virements.add(virement);
        virement.setCandidat(this);
        return this;
    }

    public Candidat removeVirement(Virement virement) {
        this.virements.remove(virement);
        virement.setCandidat(null);
        return this;
    }

    public void setVirements(Set<Virement> virements) {
        this.virements = virements;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public Candidat lessons(Set<Lesson> lessons) {
        this.lessons = lessons;
        return this;
    }

    public Candidat addLesson(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.setCandidat(this);
        return this;
    }

    public Candidat removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.setCandidat(null);
        return this;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
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
        Candidat candidat = (Candidat) o;
        if (candidat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Candidat{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
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
            ", nid='" + getNid() + "'" +
            "}";
    }
}
