package com.epam.hospital.controller.command.provider;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.impl.admin.*;
import com.epam.hospital.controller.command.impl.common.*;
import com.epam.hospital.controller.command.impl.common.page.*;
import com.epam.hospital.controller.command.impl.doctor.*;
import com.epam.hospital.controller.command.impl.admin.AddDiseaseCommand;
import com.epam.hospital.controller.command.impl.admin.BanUserCommand;
import com.epam.hospital.controller.command.impl.admin.UnbanUserCommand;
import com.epam.hospital.controller.command.impl.doctor.ConsultationApproveCommand;
import com.epam.hospital.controller.command.impl.doctor.ConsultationCompleteCommand;
import com.epam.hospital.controller.command.impl.doctor.ConsultationRejectCommand;
import com.epam.hospital.controller.command.impl.user.ConsultationRequestCommand;
import com.epam.hospital.controller.command.impl.user.*;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.CONSULTATION_PAGE, new ConsultationPageCommand());
        commands.put(CommandName.HOSPITALIZATION_PAGE, new HospitalizationPageCommand());
        commands.put(CommandName.SIGN_UP, new SignUpCommand());
        commands.put(CommandName.SIGN_OUT, new SignOutCommand());
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.CONSULTATION_COMPLETE, new ConsultationCompleteCommand());
        commands.put(CommandName.CONSULTATION_APPROVE, new ConsultationApproveCommand());
        commands.put(CommandName.CONSULTATION_REQUEST, new ConsultationRequestCommand());
        commands.put(CommandName.CONSULTATION_REJECT, new ConsultationRejectCommand());
        commands.put(CommandName.HOSPITALIZATION_REQUEST, new HospitalizationRequestCommand());
        commands.put(CommandName.HOSPITALIZATION_COMPLETE, new HospitalizationCompleteCommand());
        commands.put(CommandName.HOSPITALIZATION_REJECT, new HospitalizationRejectCommand());
        commands.put(CommandName.HOSPITALIZATION_APPROVE, new HospitalizationApproveCommand());
        commands.put(CommandName.BAN, new BanUserCommand());
        commands.put(CommandName.UNBAN, new UnbanUserCommand());
        commands.put(CommandName.LOCALIZATION, new LocalizationCommand());
        commands.put(CommandName.ADD_DISEASE, new AddDiseaseCommand());
        commands.put(CommandName.ADD_DRUG, new AddDrugCommand());
        commands.put(CommandName.ADD_DOCTOR, new AddDoctorCommand());
        commands.put(CommandName.CONSULTATION_COMPLETE_WITHOUT_COURSE, new ConsultationCompleteWithoutCourseCommand());

        commands.put(CommandName.SIGN_UP_PAGE, new ForwardPageCommand());
        commands.put(CommandName.LOGIN_PAGE, new ForwardPageCommand());
        commands.put(CommandName.ADD_DISEASE_PAGE, new ForwardPageCommand());
        commands.put(CommandName.ADD_DRUG_PAGE, new ForwardPageCommand());
        commands.put(CommandName.ADD_DOCTOR_PAGE, new ForwardPageCommand());
        commands.put(CommandName.DOCTORS, new GetAllDoctorsCommand());
        commands.put(CommandName.HOME_PAGE, new HomePageCommand());
        commands.put(CommandName.DISEASES, new GetAllDiseasesCommand());
        commands.put(CommandName.USERS, new GetAllUsersCommand());
        commands.put(CommandName.PROFILE_PAGE, new ProfilePageCommand());
        commands.put(CommandName.CONSULTATION_REQUEST_PAGE, new ConsultationRequestPageCommand());
        commands.put(CommandName.HOSPITALIZATION_REQUEST_PAGE, new HospitalizationRequestPageCommand());
        commands.put(CommandName.DISEASE, new GetDiseaseCommand());
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
