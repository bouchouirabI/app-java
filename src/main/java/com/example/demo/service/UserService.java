package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.PasswordEncoder;
import com.example.demo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeService codeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

   public Boolean addUser(User user){
        if(isUserCorrect(user)){
            user.setIsEmailVerified(false);
            user.setPassword(passwordEncoder.encodePassword(user.getPassword()));
            User addedUser = userRepository.save(user);
            codeService.add(addedUser);
            return true;
        }else
            return false;
   }

   private boolean isUserCorrect(User user){
        return validator.isEmailValide(user.getMail()) &&
                validator.isNameValide(user.getName()) &&
                validator.isNameValide(user.getLastName()) &&
                validator.isPasswordValide(user.getPassword()) &&
                validator.isPseudoValide(user.getPseudoName());
   }

}
