package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.treatment.TreatmentCourse;
import com.epam.hospital.service.validator.Validator;

import java.util.List;

public class TreatmentCourseValidatorImpl implements Validator<TreatmentCourse> {
    private static final int MAX_INSTRUCTION_SIZE = 500;
    private static final List<String> INJECTION_SYMBOLS = List.of("$", "{", "}", "<", ">");

    @Override
    public boolean isValid(TreatmentCourse entity) {
        String instruction = entity.getInstruction();
        if (instruction == null || instruction.length() > MAX_INSTRUCTION_SIZE) {
            return false;
        }

        return isValidOfInjectionAttack(instruction);
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
