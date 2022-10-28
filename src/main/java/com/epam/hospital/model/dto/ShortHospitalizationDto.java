package com.epam.hospital.model.dto;

import com.epam.hospital.model.treatment.type.HospitalizationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortHospitalizationDto {
    Integer id;
    Integer chamberNumber;
    Date date;
    HospitalizationStatus hospitalizationStatus;
    String doctorFullName;
    String patientFullName;
}
