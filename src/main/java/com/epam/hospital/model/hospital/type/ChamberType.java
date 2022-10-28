package com.epam.hospital.model.hospital.type;

import com.epam.hospital.model.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChamberType implements Entity {
    Integer chamberTypeId;
    String name;
    Integer numberOfBeds;
    Double price;
    Integer numberOfFreeRooms;
    String imageRef;
}
