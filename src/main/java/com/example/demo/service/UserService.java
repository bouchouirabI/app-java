package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.error.UserNotFound;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


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
            codeService.generateCode(addedUser);
            return true;
        }
        return false;
   }

   /*public Boolean authenticateUser(String mail, String password){
       return userRepository.findByMailAndPassword(mail,password).isPresent();
   }
    */

    public Optional<User> authenticateUser(String mail, String password){
        return userRepository.findByEmailAndPassword(mail,password);
    }

   private boolean isUserCorrect(User user){
        return validator.isEmailValid(user.getEmail()) &&
                validator.isNameValid(user.getName()) &&
                validator.isNameValid(user.getLastName()) &&
                validator.isPasswordValid(user.getPassword()) &&
                validator.isPseudoValid(user.getPseudoName()) &&
                validator.isEmailExist(user.getEmail());
   }

   public User findUserByEmail(String email){
       Optional<User> optionalUser = userRepository.findByEmail(email);
       if(optionalUser.isPresent()){
           return optionalUser.get();
       }
       throw new UserNotFound(String.format("user with email %s doesn't exist",email));
   }

   public void setEmailIsVerified(User user){
       user.setIsEmailVerified(true);
       userRepository.save(user);
   }

}
