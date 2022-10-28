package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.treatment.ChamberStaying;
import com.epam.hospital.service.validator.Validator;

import java.util.Date;

public class ChamberStayingValidatorImpl implements Validator<ChamberStaying> {

    @Override
    public boolean isValid(ChamberStaying entity) {
        Double price = entity.getPrice();
        Date date = entity.getDateIn();
        Date dateOut = entity.getDateOut();

        if (price == null || price < 0) {
            return false;
        }

        return date != null && dateOut != null;
    }
}
