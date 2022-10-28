package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.treatment.Disease;
import com.epam.hospital.service.validator.Validator;

import java.util.List;

public class DiseaseValidatorImpl implements Validator<Disease> {
    private static final int MAX_NAME_LENGTH = 500;
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    private static final List<String> INJECTION_SYMBOLS = List.of("$", "{", "}", "<", ">");

    @Override
    public boolean isValid(Disease entity) {
        String name = entity.getName();
        String description = entity.getDescription();

        if (name == null || name.length() > MAX_NAME_LENGTH) {
            return false;
        }

        if (description == null || description.length() > MAX_DESCRIPTION_LENGTH) {
            return false;
        }

        if (!isValidOfInjectionAttack(name) || !isValidOfInjectionAttack(description)) {
            return false;
        }

        return true;
    }

    public boolean isValidOfInjectionAttack(String line) {
        for (String injectSymbol : INJECTION_SYMBOLS) {
            if (line.contains(injectSymbol)) {
                return false;
            }
        }
        return true;
    }
}
