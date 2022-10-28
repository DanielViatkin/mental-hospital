package com.epam.hospital.model.treatment;

import com.epam.hospital.model.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugRecipe implements Entity {
    Integer treatmentCourseId;
    Integer drugId;
    Float dose;
    String description;
}
