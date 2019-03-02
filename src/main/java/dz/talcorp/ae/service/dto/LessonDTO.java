package dz.talcorp.ae.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import dz.talcorp.ae.domain.enumeration.TypeLesson;

/**
 * A DTO for the Lesson entity.
 */
public class LessonDTO implements Serializable {

    private Long id;

    @NotNull
    private TypeLesson typeLesson;

    @NotNull
    private LocalDate dateLesson;


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
            ", candidat=" + getCandidatId() +
            ", voiture=" + getVoitureId() +
            ", entraineur=" + getEntraineurId() +
            "}";
    }
}
