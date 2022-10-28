package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.hospital.Chamber;
import com.epam.hospital.service.validator.Validator;


public class ChamberValidatorImpl implements Validator<Chamber> {
    private static final int MIN_NUMBER_OF_FREE_BEDS = 0;
    private static final int MAX_NUMBER_OF_FREE_BEDS = 4;

    @Override
    public boolean isValid(Chamber entity) {
        int numberOfFreeBeds = entity.getNumberOfFreeBeds();

        return numberOfFreeBeds >= MIN_NUMBER_OF_FREE_BEDS && numberOfFreeBeds <= MAX_NUMBER_OF_FREE_BEDS;
    }
}
