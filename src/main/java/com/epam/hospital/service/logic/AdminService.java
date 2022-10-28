package com.epam.hospital.service.logic;

import com.epam.hospital.model.user.User;
import com.epam.hospital.model.user.info.DoctorInfo;
import com.epam.hospital.service.exception.ServiceException;

public interface AdminService {
    boolean isUserBanned(int userId) throws ServiceException;

    void setUserBanStatusById(int userId, boolean banStatus) throws ServiceException;

    int saveDoctorAndGetId(User user, DoctorInfo doctorInfo) throws ServiceException;

}
