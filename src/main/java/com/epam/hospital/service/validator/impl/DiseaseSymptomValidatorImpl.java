package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.treatment.DiseaseSymptom;
import com.epam.hospital.service.validator.Validator;

import java.util.List;

public class DiseaseSymptomValidatorImpl implements Validator<DiseaseSymptom> {
    private static final int MAX_SYMPTOMS_SIZE = 200;
    private static final List<String> INJECTION_SYMBOLS = List.of("$", "{", "}", "<", ">");

    @Override
    public boolean isValid(DiseaseSymptom entity) {
        String symptoms = entity.getSymptoms();

        if (symptoms == null || symptoms.length() > MAX_SYMPTOMS_SIZE) {
            return false;
        }

        return isValidOfInjectionAttack(symptoms);
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
