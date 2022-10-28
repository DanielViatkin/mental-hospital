package com.epam.hospital.model.hospital;

import com.epam.hospital.model.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital implements Entity {
    Integer hospital_id;
    String address;
    String phone_number;

}
