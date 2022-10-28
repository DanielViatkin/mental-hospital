package com.epam.hospital.controller.command.impl.common.page;

import com.epam.hospital.constant.web.Page;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.dto.HospitalizationDto;
import com.epam.hospital.model.hospital.Chamber;
import com.epam.hospital.model.treatment.ChamberStaying;
import com.epam.hospital.model.treatment.Hospitalization;
import com.epam.hospital.model.user.User;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.ChamberService;
import com.epam.hospital.service.logic.HospitalizationService;
import com.epam.hospital.service.logic.PatientCardService;
import com.epam.hospital.service.logic.UserService;
import com.epam.hospital.service.logic.impl.ChamberServiceImpl;
import com.epam.hospital.service.logic.impl.HospitalizationServiceImpl;
import com.epam.hospital.service.logic.impl.PatientCardServiceImpl;
import com.epam.hospital.service.logic.impl.UserServiceImpl;


public class HospitalizationPageCommand implements Command {
    private final static HospitalizationService hospitalizationService = HospitalizationServiceImpl.getInstance();
    private final static ChamberService chamberService = ChamberServiceImpl.getInstance();
    private final static UserService userService = UserServiceImpl.getInstance();
    private final static PatientCardService patientCardService = PatientCardServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        int hospitalizationId = ParameterExtractor.extractInt(RequestParameters.HOSPITALIZATION_ID, requestContext);

        requestContext.addAttribute(RequestAttributes.HOSPITALIZATION_ID, hospitalizationId);

        Hospitalization hospitalization = hospitalizationService.getHospitalizationById(hospitalizationId);
        ChamberStaying chamberStaying = chamberService.getChamberStayingByHospitalizationId(hospitalizationId);
        User doctor = userService.getUserById(hospitalization.getDoctorId());
        int patientId = patientCardService.getPatientCardById(hospitalization.getPatientId()).getUserId();
        User patient = userService.getUserById(patientId);
        Chamber chamber = chamberService.getChamberById(chamberStaying.getChamberId());

        HospitalizationDto hospitalizationDto = HospitalizationDto.builder()
                .dateIn(chamberStaying.getDateIn())
                .dateOut(chamberStaying.getDateOut())
                .doctorFirstName(doctor.getFirstName())
                .doctorLastName(doctor.getLastName())
                .patientLastName(patient.getLastName())
                .patientFirstName(patient.getFirstName())
                .chamberNumber(chamberStaying.getChamberId())
                .chamberType(chamberService.getChamberTypeById(chamber.getChamberTypeId()))
                .doctorId(hospitalization.getDoctorId())
                .id(hospitalization.getId())
                .patientId(patientId)
                .status(hospitalization.getStatus())
                .price(chamberStaying.getPrice())
                .build();

        requestContext.addAttribute(RequestAttributes.HOSPITALIZATION, hospitalizationDto);
        return CommandResult.forward(Page.HOSPITALIZATION_PAGE);
    }
}
