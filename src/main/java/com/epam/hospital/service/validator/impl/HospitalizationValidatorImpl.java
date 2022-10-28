package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.treatment.Hospitalization;
import com.epam.hospital.model.treatment.type.HospitalizationStatus;
import com.epam.hospital.service.validator.Validator;

import java.util.List;

public class HospitalizationValidatorImpl implements Validator<Hospitalization> {
    private static final List<String> INJECTION_SYMBOLS = List.of("$", "{", "}", "<", ">");

    @Override
    public boolean isValid(Hospitalization entity) {
        HospitalizationStatus hospitalizationStatus = entity.getStatus();

        if (hospitalizationStatus == null) {
            return false;
        }

        return isValidOfInjectionAttack(hospitalizationStatus.toString());
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
