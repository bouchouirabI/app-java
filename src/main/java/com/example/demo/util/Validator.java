package com.example.demo.util;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Validator {
    private final String VALID_EMAIL_ADDRESS_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    private final String VALID_PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

    private final String VALID_NAME_REGEX = "^([a-zA-Z])+(.{2,})+$";

    private final String VALID_PSEUDO_NAME_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)";


    public Boolean isEmailValide(String email){
        return validate(email, VALID_EMAIL_ADDRESS_REGEX);
    }

    public Boolean isPasswordValide(String password){
        return validate(password, VALID_PASSWORD_REGEX);
    }

    public Boolean isNameValide(String name){
        return validate(name, VALID_NAME_REGEX);
    }

    public Boolean isPseudoValide(String pseudo){
        return validate(pseudo, VALID_PSEUDO_NAME_REGEX);
    }

    private boolean validate(String value, String regex) {
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

}
