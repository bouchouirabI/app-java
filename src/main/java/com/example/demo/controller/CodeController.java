package com.example.demo.controller;

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

    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestParam String email, @RequestParam Integer value){
        return codeService.isCodeCorrect(email, value)
                ? new ResponseEntity<>("ok", HttpStatus.FOUND)
                : new ResponseEntity<>("your code incorrect ", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/newCode")
    public ResponseEntity<String> newCode(@RequestParam String email){
        codeService.generateNewCode(email);
        return new ResponseEntity<>("ok",HttpStatus.CREATED);
    }
}

