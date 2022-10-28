package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.treatment.PatientCard;
import com.epam.hospital.service.validator.Validator;

import java.util.List;

public class PatientCardValidatorImpl implements Validator<PatientCard> {
    private static final int MAX_SPARE_NUMBER_LENGTH = 45;
    private static final int MIN_AGE = 18;
    private static final String MALE = "male";
    private static final String FEMALE = "female";
    private static final List<String> INJECTION_SYMBOLS = List.of("$", "{", "}", "<", ">");

    @Override
    public boolean isValid(PatientCard entity) {
        String spareNumber = entity.getSpareNumber();
        int age = entity.getAge();
        String sex = entity.getSex();

        if (spareNumber == null || spareNumber.length() > MAX_SPARE_NUMBER_LENGTH) {
            return false;
        }

        if (!(sex.equalsIgnoreCase(MALE) || sex.equalsIgnoreCase(FEMALE))) {
            return false;
        }

        if (!isValidOfInjectionAttack(spareNumber) || !isValidOfInjectionAttack(sex)) {
            return false;
        }

        return age >= MIN_AGE;
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
