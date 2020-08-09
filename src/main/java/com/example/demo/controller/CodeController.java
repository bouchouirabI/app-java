package com.example.demo.controller;

import com.example.demo.entity.Code;
import com.example.demo.entity.CodeStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.CodeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/codes")
public class CodeController {
    @Autowired
    public CodeService codeService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public CodeRepository codeRepository;

    @PostMapping("/verified")
    public ResponseEntity<String> verifiedCode(@RequestParam String email ,@RequestParam Integer value){
        User user = userRepository.findByMail(email);
        Code codeVerified = codeService.isCodeVerified(value ,user);
        if(codeVerified == null){
            return new ResponseEntity<>("your code incorrect ", HttpStatus.NO_CONTENT);
        }
        else {
            codeVerified.setStatus(CodeStatus.ENABLED);
            codeRepository.save(codeVerified);

            user.setIsEmailVerified(true);
            userRepository.save(user);

            return new ResponseEntity<>("ok", HttpStatus.FOUND);
        }
    }

    @GetMapping("/newCode")
    public void newCode(@RequestParam String email){
        User user = userRepository.findByMail(email);
        codeService.isNewCode(user);
    }
}

