package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
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
    private Validator validator;

   public Boolean addUser(User user){
        if(isUserCorrect(user)){
            user.setIsEmailVerified(false);
            user.setPassword(user.getPassword());
            User addedUser = userRepository.save(user);
            codeService.add(addedUser);
            return true;
        }
        return false;
   }

   public Boolean authenticateUser(String mail, String password){
       return userRepository.findByMailAndPassword(mail,password).isPresent();
   }

   private boolean isUserCorrect(User user){
        return validator.isEmailValid(user.getMail()) &&
                validator.isNameValid(user.getName()) &&
                validator.isNameValid(user.getLastName()) &&
                validator.isPasswordValid(user.getPassword()) &&
                validator.isPseudoValid(user.getPseudoName()) &&
                validator.isEmailExist(user.getMail());
   }

}
