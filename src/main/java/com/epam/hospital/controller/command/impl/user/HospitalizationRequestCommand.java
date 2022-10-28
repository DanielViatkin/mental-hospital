package com.epam.hospital.controller.command.impl.user;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.constant.web.SessionAttributes;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.hospital.Chamber;
import com.epam.hospital.model.hospital.type.ChamberType;
import com.epam.hospital.model.treatment.ChamberStaying;
import com.epam.hospital.model.treatment.Hospitalization;
import com.epam.hospital.model.treatment.type.HospitalizationStatus;
import com.epam.hospital.model.user.User;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.ChamberService;
import com.epam.hospital.service.logic.HospitalizationService;
import com.epam.hospital.service.logic.UserService;
import com.epam.hospital.service.logic.impl.ChamberServiceImpl;
import com.epam.hospital.service.logic.impl.HospitalizationServiceImpl;
import com.epam.hospital.service.logic.impl.UserServiceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HospitalizationRequestCommand implements Command {
    private static final String HOSPITALIZATION_REQUEST_PAGE_COMMAND = "MentalHospital?command=" + CommandName.HOSPITALIZATION_REQUEST_PAGE;
    private static final String HOSPITALIZATION_PAGE_COMMAND = "MentalHospital?command=" + CommandName.HOSPITALIZATION_PAGE;
    private static final String CHAMBER_TYPE_UNAVAILABLE = "unavailable.chamber.type";
    private static final HospitalizationService hospitalizationService = HospitalizationServiceImpl.getInstance();
    private static final ChamberService chamberService = ChamberServiceImpl.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        int patientCardId = (Integer) requestContext.getSessionAttribute(SessionAttributes.PATIENT_CARD_ID);

        int chamberTypeId = ParameterExtractor.extractInt(RequestParameters.CHAMBER_TYPE_ID, requestContext);
        ChamberType chamberType = chamberService.getChamberTypeById(chamberTypeId);
        int numberOfFreeRooms = chamberType.getNumberOfFreeRooms();
        if(numberOfFreeRooms >= 1) {
            Chamber chamber = chamberService.getAvailableChamber(chamberTypeId);
            int chamberId = chamber.getChamberId();
            Integer numberOfFreeBeds = chamber.getNumberOfFreeBeds();
            if (numberOfFreeBeds.equals(1)){
                int currentlyNumberOfFreeRooms = chamberType.getNumberOfFreeRooms();
                chamberType.setNumberOfFreeRooms(currentlyNumberOfFreeRooms-1);
            }


            String doctorName = ParameterExtractor.extractString(RequestParameters.DOCTOR, requestContext);
            List<String> fullName = new ArrayList<>(Arrays.asList(doctorName.split(" ")));
            User doctor = userService.getUserByFullName(fullName.get(0), fullName.get(1));

            Hospitalization hospitalization = Hospitalization.builder()
                    .patientId(patientCardId)
                    .status(HospitalizationStatus.PENDING)
                    .doctorId(doctor.getUserId())
                    .build();

            ChamberStaying chamberStaying = ChamberStaying.builder()
                    .chamberId(chamberId)
                    .dateIn(new Date(System.currentTimeMillis()))
                    .dateOut(new Date(System.currentTimeMillis()))
                    .price(0.0)
                    .build();

            chamber.setNumberOfFreeBeds(numberOfFreeBeds-1);
            int id;
            if (numberOfFreeBeds.equals(1)){
                id = hospitalizationService.saveHospitalizationAndGetId(hospitalization, chamberStaying, chamber, chamberType);
            } else {
                id = hospitalizationService.saveHospitalizationAndGetId(hospitalization, chamberStaying, chamber);

            }
            return CommandResult.redirect(HOSPITALIZATION_PAGE_COMMAND + "&id=" + id);
        } else {
            requestContext.addAttribute(RequestAttributes.ERROR_MESSAGE, CHAMBER_TYPE_UNAVAILABLE);
        }

        return CommandResult.forward(HOSPITALIZATION_REQUEST_PAGE_COMMAND);
    }
}
