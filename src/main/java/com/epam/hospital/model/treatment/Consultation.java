package com.epam.hospital.model.treatment;

import com.epam.hospital.model.Entity;
import com.epam.hospital.model.treatment.type.CommunicationType;
import com.epam.hospital.model.treatment.type.ConsultationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Consultation implements Entity {
    Integer consultationId;
    Integer doctorId;
    Integer patientId;
    CommunicationType communicationType;
    Timestamp date;
    Integer duration;
    Integer treatmentCourseId;
    Double price;
    ConsultationStatus status;
    Integer parentConsultationId;
    Integer childConsultationId;
}
