package com.epam.hospital.service.logic;

import com.epam.hospital.model.treatment.PatientCard;
import com.epam.hospital.model.user.User;
import com.epam.hospital.service.exception.ServiceException;

public interface SignUpService {
    boolean signUp(User user, PatientCard patientCard) throws ServiceException;
}
