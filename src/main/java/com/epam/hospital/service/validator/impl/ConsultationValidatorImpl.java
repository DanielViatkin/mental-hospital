package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.treatment.Consultation;
import com.epam.hospital.model.treatment.type.CommunicationType;
import com.epam.hospital.model.treatment.type.ConsultationStatus;
import com.epam.hospital.service.validator.Validator;

import java.util.List;

public class ConsultationValidatorImpl implements Validator<Consultation> {
    private static final List<String> INJECTION_SYMBOLS = List.of("$", "{", "}", "<", ">");

    @Override
    public boolean isValid(Consultation entity) {
        CommunicationType communicationType = entity.getCommunicationType();
        ConsultationStatus consultationStatus = entity.getStatus();

        if (communicationType == null ||
                consultationStatus == null) {
            return false;
        }

        return isValidOfInjectionAttack(communicationType.toString()) &&
                isValidOfInjectionAttack(consultationStatus.toString());
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
