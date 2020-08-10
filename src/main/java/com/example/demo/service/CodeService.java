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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private  MailService mailService;

    public void generateCode(User user){
        Integer randomValue = RandomUtils.nextInt(1000,9999);
        LocalDateTime creationDateTime = LocalDateTime.now();
        LocalDateTime expirationDateTime = creationDateTime.plusMinutes(30l);
        Code code =  Code.builder()
                .value(randomValue)
                .user(user)
                .status(CodeStatus.NOT_VERIFIED)
                .creationDate(creationDateTime)
                .expirationDate(expirationDateTime).build();
        codeRepository.save(code);
        mailService.sendCodeByMail(code ,user.getEmail());
    }

    public void generateNewCode(String email){
        User user = userService.findUserByEmail(email);
        List<Code> expiredCodes = codeRepository.findByUserAndStatus(user,CodeStatus.NOT_VERIFIED)
                .stream().map(
                        code -> {
                            code.setStatus(CodeStatus.EXPIRED);
                            return code;
                        }).collect(Collectors.toList());

        codeRepository.saveAll(expiredCodes);
        generateCode(user);
    }

    public boolean isCodeCorrect(String email, Integer codeValue){
        User user = userService.findUserByEmail(email);
        Optional<Code> codeToVerify = codeRepository.findByUserAndValueAndStatus(user, codeValue, CodeStatus.NOT_VERIFIED);
        AtomicBoolean isCodeCorrect = new AtomicBoolean(false);
        codeToVerify.ifPresent(code -> {
            if(code.getExpirationDate().isAfter(LocalDateTime.now())){
                code.setStatus(CodeStatus.VERIFIED);
                codeRepository.save(code);
                userService.setEmailIsVerified(user);
                isCodeCorrect.set(true);
            }
            code.setStatus(CodeStatus.EXPIRED);
            codeRepository.save(code);
        });

        return isCodeCorrect.get();
    }
}
