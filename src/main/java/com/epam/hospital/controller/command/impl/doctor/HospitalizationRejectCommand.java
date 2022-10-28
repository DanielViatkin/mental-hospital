package com.epam.hospital.controller.command.impl.doctor;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.hospital.Chamber;
import com.epam.hospital.model.hospital.type.ChamberType;
import com.epam.hospital.model.treatment.ChamberStaying;
import com.epam.hospital.model.treatment.Hospitalization;
import com.epam.hospital.model.treatment.type.HospitalizationStatus;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.ChamberService;
import com.epam.hospital.service.logic.HospitalizationService;
import com.epam.hospital.service.logic.impl.ChamberServiceImpl;
import com.epam.hospital.service.logic.impl.HospitalizationServiceImpl;

public class HospitalizationRejectCommand implements Command {
    HospitalizationService hospitalizationService = new HospitalizationServiceImpl();
    ChamberService chamberService = new ChamberServiceImpl();
    private static final String HOSPITALIZATION_PAGE_COMMAND = "MentalHospital?command=" + CommandName.HOSPITALIZATION_PAGE;

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        int hospitalizationId = ParameterExtractor.extractInt(RequestParameters.HOSPITALIZATION_ID, requestContext);
        Hospitalization hospitalization = hospitalizationService.getHospitalizationById(hospitalizationId);
        hospitalization.setStatus(HospitalizationStatus.REJECTED);
        hospitalizationService.update(hospitalization);

        int chamberId = chamberService.getChamberStayingByHospitalizationId(hospitalizationId).getChamberId();
        Chamber chamber = chamberService.getChamberById(chamberId);
        if (chamber.getNumberOfFreeBeds() == 0){
            ChamberType chamberType = chamberService.getChamberTypeById(chamber.getChamberTypeId());
            int currentNumOfFreeRooms = chamberType.getNumberOfFreeRooms();
            chamberType.setNumberOfFreeRooms(currentNumOfFreeRooms+1);
            chamberService.updateChamberType(chamberType);
        }
        int currentNumOfFreeBeds = chamber.getNumberOfFreeBeds();
        chamber.setNumberOfFreeBeds(currentNumOfFreeBeds + 1);
        chamberService.updateChamber(chamber);
        return CommandResult.redirect(HOSPITALIZATION_PAGE_COMMAND + "&id=" + hospitalizationId);
    }
}
