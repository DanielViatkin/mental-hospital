package com.epam.hospital.controller.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandResult {
    String page;
    boolean isRedirect;

    public static CommandResult redirect(String page) {
        return new CommandResult(page, true);
    }

    public static CommandResult forward(String page) {
        return new CommandResult(page, false);
    }
}
