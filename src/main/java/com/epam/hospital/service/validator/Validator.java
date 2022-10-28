package com.epam.hospital.service.validator;

import com.epam.hospital.model.Entity;

public interface Validator<T extends Entity> {
    boolean isValid(T entity);
}
