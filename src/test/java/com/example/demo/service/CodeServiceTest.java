package com.example.demo.service;

import com.example.demo.entity.Code;
import com.example.demo.entity.CodeStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.CodeRepository;
import com.example.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CodeServiceTest {
    @Mock
    private CodeRepository codeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CodeService codeService;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private MailService mailService;

    @Test
    public void Should_generateCode(){
        User user = new User(2,"name","lastname","pdname","ibi.game20@gmail.com","12A3456@8",false);
        codeService.generateCode(user);
        Code code = new Code(1,user,8888, LocalDateTime.now(),LocalDateTime.now(), CodeStatus.NOT_VERIFIED);
        mailService.sendCodeByMail(code, user.getEmail());
        Mockito.verify(codeRepository,Mockito.times(1)).save(Mockito.any(Code.class));
    }

    @Test
    public void Should_generateNewCode(){
        when(userRepository.findByEmail("ibi.game20@gmail.com")).thenReturn(Optional.of(User.builder().build()));
        Assertions.assertThat(userService.findUserByEmail("ibi.game20@gmail.com")).isNotNull();
        //..
    }

    @Test
    public void Should_return_isCodeCorrect(){
        Assertions.assertThat(codeService.isCodeCorrect("ibi.game20@gmail.com",3344)).isTrue();
    }

}
