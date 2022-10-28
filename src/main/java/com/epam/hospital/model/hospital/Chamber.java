package com.epam.hospital.model.hospital;

import com.epam.hospital.model.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chamber implements Entity {
    Integer chamberId;
    Integer hospitalId;
    Integer chamberTypeId;
    Integer numberOfFreeBeds;

}
