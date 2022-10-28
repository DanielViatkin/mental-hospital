package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.hospital.type.ChamberType;
import com.epam.hospital.service.validator.Validator;

import java.util.List;

public class ChamberTypeValidatorImpl implements Validator<ChamberType> {
    private static final int MIN_NUMBER_BEDS_VALUE = 1;
    private static final List<String> INJECTION_SYMBOLS = List.of("$", "{", "}", "<", ">");


    @Override
    public boolean isValid(ChamberType chamberType) {
        String name = chamberType.getName();
        int num_of_beds = chamberType.getNumberOfBeds();

        if (name == null || !isValidOfInjectionAttack(name)){
            return false;
        }

        return num_of_beds >= MIN_NUMBER_BEDS_VALUE;
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
