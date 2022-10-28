package com.epam.hospital.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDto {
    String userRole;
    Integer doctorId;
    String email;
    String firstName;
    String lastName;
    String number;
    String specialization;
    Integer workExperience;
    Integer classification;
}
