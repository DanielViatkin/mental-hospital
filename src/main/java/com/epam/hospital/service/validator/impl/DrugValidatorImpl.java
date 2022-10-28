package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.treatment.Drug;
import com.epam.hospital.service.validator.Validator;

import java.util.List;

public class DrugValidatorImpl implements Validator<Drug> {
    private static final int MAX_TITLE_LENGTH = 45;
    private static final int MIN_ID_VALUE = 0;
    private static final List<String> INJECTION_SYMBOLS = List.of("$", "{", "}", "<", ">");

    @Override
    public boolean isValid(Drug entity) {
        String title = entity.getName();

        if (title == null || title.length() > MAX_TITLE_LENGTH) {
            return false;
        }

        return isValidOfInjectionAttack(title);
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
