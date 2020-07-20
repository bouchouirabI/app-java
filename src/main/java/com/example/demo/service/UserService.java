package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeService codeService;

   private final String VALID_EMAIL_ADDRESS_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

   private final String VALID_PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

   private final String VALID_NAME_REGEX = "^([a-zA-Z])+(.{2,})+$";

   private final String VALID_PSEUDO_NAME_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)";

   public Boolean addUser(User user){
        if(isUserCorrect(user)){
            user.setIsEmailVerified(false);
            User addedUser = userRepository.save(user);
            codeService.add(addedUser);
            return true;
        }else
            return false;
   }

   private boolean isUserCorrect(User user){
        return validate(user.getName(),VALID_NAME_REGEX) &&
                validate(user.getLastName(),VALID_NAME_REGEX) &&
                validate(user.getPseudoName(),VALID_PSEUDO_NAME_REGEX) &&
                validate(user.getPassword(),VALID_PASSWORD_REGEX) &&
                validate(user.getMail(),VALID_EMAIL_ADDRESS_REGEX)
                ? true : false;
   }

   private boolean validate(String value, String regex) {
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
   }




}
