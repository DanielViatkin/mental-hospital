package com.epam.hospital.model.dto;

import com.epam.hospital.model.Entity;
import com.epam.hospital.model.treatment.type.ConsultationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationDto implements Entity {
    String communicationType;
    Date date;
    Integer duration;
    ConsultationStatus consultationStatus;
    String doctorFirstName;
    String doctorLastName;
    Integer doctorId;
    Integer patientId;
    String patientFirstName;
    String patientLastName;
    List<DiseaseWithSymptomsDto> diseases;
    List<DrugRecipeDto> drugs;
    String instruction;
    Double price;
    boolean courseExist;
}
