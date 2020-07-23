package com.example.demo.service;

import com.example.demo.entity.Code;
import com.example.demo.entity.CodeStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.CodeRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private  MailService mailService;

    public void add(User user){
        Integer randomValue = RandomUtils.nextInt(1000,9999);
        LocalDateTime creationDateTime = LocalDateTime.now();
        LocalDateTime expirationDateTime = creationDateTime.plusMinutes(30l);
        Code code =  Code.builder()
                .value(randomValue)
                .user(user)
                .status(CodeStatus.DISABLED)
                .creationDate(creationDateTime)
                .expirationDate(expirationDateTime).build();
        codeRepository.save(code);
        mailService.sendCodeByMail(code ,user.getMail());
    }

    public Boolean isCodeVerified( int valueCode , User user, CodeStatus status) {
        Code code = codeRepository.findByUserAndStatus(user, status);
        LocalDateTime DateTime = LocalDateTime.now();
        if(code.getExpirationDate().isAfter(DateTime)){
            return  code.getValue().equals(valueCode);
        }
        return false;

    }


}
