package com.epam.hospital.service.validator.impl;

import com.epam.hospital.model.user.User;
import com.epam.hospital.service.validator.Validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidatorImpl implements Validator<User> {
    private static final String NAME_PATTERN = "[A-zА-яЁё]+";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final Pattern COMPILED_PATTERN_NAME = Pattern.compile(NAME_PATTERN);
    private static final Pattern COMPILED_PATTERN_EMAIL = Pattern.compile(EMAIL_PATTERN);
    private static final int MIN_FIELD_USER_LENGTH = 2;
    private static final int MAX_FIELD_USER_LENGTH = 32;
    private static final int MIN_LOGIN_LENGTH = 8;
    private static final int MIN_PHONE_LENGTH = 12;
    private static final int MAX_PHONE_NUMBER_LENGTH = 18;
    private static final int MAX_LOGIN_LENGTH = 64;
    private static final int MAX_PASSWORD_LENGTH = 32;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final List<String> INJECTION_SYMBOLS = List.of("$", "{", "}", "<", ">");
    private static final int HASHED_PASSWORD_LENGTH = 128;

    @Override
    public boolean isValid(User user) {
        if (!isValidGeneralInfo(user)) {
            return false;
        }
        String login = user.getEmail();
        String password = user.getHashedPassword();
        if (login == null || login.length() > MAX_FIELD_USER_LENGTH || login.length() < MIN_LOGIN_LENGTH || !isValidOfInjectionAttack(login) || !isValidEmail(login)) {
            return false;
        }
        return password != null && password.length() == HASHED_PASSWORD_LENGTH;
    }

    public boolean isValidGeneralInfo(User user) {
        if (user.getFirstName() == null
                || user.getFirstName().length() > MAX_FIELD_USER_LENGTH
                || user.getFirstName().length() < MIN_FIELD_USER_LENGTH
                || !isValidName(user.getFirstName())
                || !isValidOfInjectionAttack(user.getFirstName())) {
            return false;
        }
        if (user.getLastName() == null
                || user.getLastName().length() > MAX_FIELD_USER_LENGTH
                || user.getLastName().length() < MIN_FIELD_USER_LENGTH
                || !isValidName(user.getLastName())
                || !isValidOfInjectionAttack(user.getLastName())) {
            return false;
        }
        String phone = String.valueOf(user.getNumber());
        if (phone.length() > MAX_PHONE_NUMBER_LENGTH || phone.length() < MIN_PHONE_LENGTH) {
            return false;
        }
        return true;
    }

    public boolean isValidPassword(String line) {
        if (line == null || line.length() > MAX_PASSWORD_LENGTH
                || line.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }
        return true;
    }

    private boolean isValidName(String name) {
        Matcher matcher = COMPILED_PATTERN_NAME.matcher(name);
        return matcher.matches();
    }

    public boolean isValidOfInjectionAttack(String line) {
        for (String injectSymbol : INJECTION_SYMBOLS) {
            if (line.contains(injectSymbol)) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidEmail(String email) {
        Matcher matcher = COMPILED_PATTERN_EMAIL.matcher(email);
        return matcher.matches();
    }
}
