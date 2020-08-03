package com.example.demo.util;

import com.example.demo.error.NotValidData;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Validator {
    @Autowired
    UserRepository userRepository;

    private final String VALID_EMAIL_ADDRESS_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    private final String VALID_PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

    private final String VALID_NAME_REGEX = "^([a-zA-Z])+(.{2,})+$";

    private final String VALID_PSEUDO_NAME_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)";


    public Boolean isEmailValid(String email){
        if(validate(email, VALID_EMAIL_ADDRESS_REGEX)){
            return true;
        }
        throw new NotValidData("The format of email is not correct");
    }

    public Boolean isPasswordValid(String password){
        if(validate(password, VALID_PASSWORD_REGEX)) {
            return true;
        }
        throw new NotValidData("The format of password is not correct");
    }

    public Boolean isNameValid(String name){
        if(validate(name, VALID_NAME_REGEX)){
            return true;
        }
        throw new NotValidData("The format of name is not correct");
    }

    public Boolean isPseudoValid(String pseudo){
        if(validate(pseudo, VALID_PSEUDO_NAME_REGEX)) {
            return true;
        }
        throw new NotValidData("The format of pseudo_name is not correct");
    }

    public Boolean isEmailExist(String mail){
        if(userRepository.findByMail(mail) == null) {
            return true;
        }
        throw new NotValidData("Email already exists ");
    }

    private boolean validate(String value, String regex) {
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

}
