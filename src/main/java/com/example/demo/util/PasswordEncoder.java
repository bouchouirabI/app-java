package com.example.demo.util;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder {
    String pepper = "pepper";
    int iterations = 200000;  // number of hash iteration
    int hashWidth = 256;

    public String encodePassword(String password){
        Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(pepper, iterations, hashWidth);
        passwordEncoder.setEncodeHashAsBase64(true);
        return passwordEncoder.encode(password);
    }
}
