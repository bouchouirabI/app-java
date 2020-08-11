package com.example.demo.service;

import com.example.demo.entity.Code;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {

    @InjectMocks
    private MailService mailService;

    @Test
    public void should_sendEmail(){
        Mockito.verify(mailService,Mockito.timeout(1)).sendCodeByMail(Mockito.any(Code.class),"ibi.game20@gmail.com");
    }
}
