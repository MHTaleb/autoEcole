package dz.talcorp.ae.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import dz.talcorp.ae.domain.enumeration.TypeLesson;
import dz.talcorp.ae.domain.enumeration.EtatLesson;

/**
 * A DTO for the Lesson entity.
 */
public class LessonDTO implements Serializable {

    private Long id;

    @NotNull
    private TypeLesson typeLesson;

    @NotNull
    private LocalDate dateLesson;

    @NotNull
    private Instant heurLesson;

    @NotNull
    private EtatLesson etatLesson;

    @Lob
    private String observation;


    private Long candidatId;

    private Long voitureId;

    private Long entraineurId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeLesson getTypeLesson() {
        return typeLesson;
    }

    public void setTypeLesson(TypeLesson typeLesson) {
        this.typeLesson = typeLesson;
    }

    public LocalDate getDateLesson() {
        return dateLesson;
    }

    public void setDateLesson(LocalDate dateLesson) {
        this.dateLesson = dateLesson;
    }

    public Instant getHeurLesson() {
        return heurLesson;
    }

    public void setHeurLesson(Instant heurLesson) {
        this.heurLesson = heurLesson;
    }

    public EtatLesson getEtatLesson() {
        return etatLesson;
    }

    public void setEtatLesson(EtatLesson etatLesson) {
        this.etatLesson = etatLesson;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(Long candidatId) {
        this.candidatId = candidatId;
    }

    public Long getVoitureId() {
        return voitureId;
    }

    public void setVoitureId(Long carId) {
        this.voitureId = carId;
    }

    public Long getEntraineurId() {
        return entraineurId;
    }

    public void setEntraineurId(Long entraineurId) {
        this.entraineurId = entraineurId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LessonDTO lessonDTO = (LessonDTO) o;
        if (lessonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lessonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LessonDTO{" +
            "id=" + getId() +
            ", typeLesson='" + getTypeLesson() + "'" +
            ", dateLesson='" + getDateLesson() + "'" +
            ", heurLesson='" + getHeurLesson() + "'" +
            ", etatLesson='" + getEtatLesson() + "'" +
            ", observation='" + getObservation() + "'" +
            ", candidat=" + getCandidatId() +
            ", voiture=" + getVoitureId() +
            ", entraineur=" + getEntraineurId() +
            "}";
    }
}
