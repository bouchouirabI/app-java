package com.example.demo.service;

import com.example.demo.entity.Code;
import com.example.demo.entity.CodeStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.CodeRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public Code isCodeVerified( int valueCode , User user) {
        Code code = codeRepository.findByUserAndStatus(user,CodeStatus.DISABLED );
        LocalDateTime DateTime = LocalDateTime.now();
        if(code.getExpirationDate().isAfter(DateTime) && code.getValue().equals(valueCode)){
            return code ;
        }
        return null;
    }

    public void isNewCode(User user){
        List<Code> listCode = codeRepository.findByUser(user);
        for( int i=0 ; i<listCode.size(); i++) {
            listCode.get(i).setStatus(CodeStatus.ENABLED);
            codeRepository.save(listCode.get(i));
        }
        add(user);
    }


}
