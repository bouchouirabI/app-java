package com.example.demo.controller;

import com.example.demo.service.CodeService;
import com.example.demo.service.UserService;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    public UserService userService;

    @Autowired
    public CodeService codeService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        Boolean isUserAdded = userService.addUser(user);
        return isUserAdded ? new ResponseEntity<>("ok", HttpStatus.CREATED)
                : new ResponseEntity<>("not ok", HttpStatus.CONFLICT);
    }

    /*@PostMapping("/auth")
    public ResponseEntity<String> authenticateUser(@RequestParam String email, @RequestParam String password){
        Boolean isUserAuthenticated = userService.authenticateUser(email, password);
        return isUserAuthenticated ? new ResponseEntity<>("ok", HttpStatus.ACCEPTED)
                : new ResponseEntity<>("your mail or your password not correct", HttpStatus.CONFLICT);
    }
     */

     @PostMapping("/auth")
     public ResponseEntity<String> authenticateUser(@RequestParam String email, @RequestParam String password){
        User userAuthenticated = userService.authenticateUser(email, password);
        if(userAuthenticated == null){
            return new ResponseEntity<>("your mail or your password not correct", HttpStatus.CONFLICT);
        }
        else {
            if(userAuthenticated.getIsEmailVerified()){
                return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
            }
            else

                return new ResponseEntity<>("Verified your Email", HttpStatus.CONFLICT);
        }

     }


}
