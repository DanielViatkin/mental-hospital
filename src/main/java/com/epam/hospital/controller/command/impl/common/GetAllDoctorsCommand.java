package com.epam.hospital.controller.command.impl.common;

import com.epam.hospital.constant.web.Page;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.dto.DoctorDto;
import com.epam.hospital.model.user.User;
import com.epam.hospital.model.user.info.DoctorInfo;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.UserService;
import com.epam.hospital.service.logic.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class GetAllDoctorsCommand implements Command {
    private final static UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<User> doctorsList = userService.getAllDoctors(3);
        List<DoctorInfo> doctorsInfoList = new ArrayList<>();
        for (User doctor : doctorsList) {
            int doctorId = doctor.getUserId();
            DoctorInfo doctorInfo = userService.getDoctorInfoById(doctorId);
            doctorsInfoList.add(doctorInfo);
        }
        List<DoctorDto> doctorDtos = new ArrayList<>();
        for (int i = 0; i < doctorsInfoList.size() && i < doctorsList.size(); i++) {
            DoctorDto doctorDto = DoctorDto.builder()
                    .workExperience(doctorsInfoList.get(i).getWorkExperience())
                    .specialization(doctorsInfoList.get(i).getSpecialization())
                    .classification(doctorsInfoList.get(i).getClassification())
                    .userRole("DOCTOR")
                    .doctorId(doctorsInfoList.get(i).getDoctorId())
                    .number(doctorsList.get(i).getNumber())
                    .firstName(doctorsList.get(i).getFirstName())
                    .lastName(doctorsList.get(i).getLastName())
                    .number(doctorsList.get(i).getNumber())
                    .build();
            doctorDtos.add(doctorDto);
        }

        log.info("doctors: {}", doctorDtos);

        requestContext.addAttribute(RequestAttributes.DOCTORS, doctorDtos);
        return CommandResult.forward(Page.DOCTORS);
    }
}
