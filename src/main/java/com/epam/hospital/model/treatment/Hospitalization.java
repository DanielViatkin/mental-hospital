package com.epam.hospital.model.treatment;

import com.epam.hospital.model.Entity;
import com.epam.hospital.model.treatment.type.HospitalizationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hospitalization implements Entity {
    Integer id;
    Integer patientId;
    Integer doctorId;
    HospitalizationStatus status;
}
