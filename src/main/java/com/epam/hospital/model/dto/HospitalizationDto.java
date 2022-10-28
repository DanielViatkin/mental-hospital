package com.epam.hospital.model.dto;

import com.epam.hospital.model.hospital.type.ChamberType;
import com.epam.hospital.model.treatment.type.HospitalizationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalizationDto {
    Integer id;
    Double price;
    Integer doctorId;
    Integer patientId;
    String doctorFirstName;
    String doctorLastName;
    String patientFirstName;
    String patientLastName;
    Integer chamberNumber;
    ChamberType chamberType;
    HospitalizationStatus status;
    Date dateIn;
    Date dateOut;
}
