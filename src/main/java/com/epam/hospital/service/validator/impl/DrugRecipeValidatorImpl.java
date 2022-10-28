package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.treatment.DrugRecipe;
import com.epam.hospital.service.validator.Validator;

import java.util.List;

public class DrugRecipeValidatorImpl implements Validator<DrugRecipe> {
    private static final int MAX_DESCRIPTION_SIZE = 500;
    private static final int MIN_DOSE = 1;
    private static final List<String> INJECTION_SYMBOLS = List.of("$", "{", "}", "<", ">");

    @Override
    public boolean isValid(DrugRecipe entity) {
        float dose = entity.getDose();
        String description = entity.getDescription();

        if (dose < MIN_DOSE) {
            return false;
        }

        if (description == null || description.length() > MAX_DESCRIPTION_SIZE) {
            return false;
        }

        return isValidOfInjectionAttack(description);
    }

    private boolean isValidOfInjectionAttack(String line) {
        for (String injectSymbol : INJECTION_SYMBOLS) {
            if (line.contains(injectSymbol)) {
                return false;
            }
        }
        return true;
    }
}
