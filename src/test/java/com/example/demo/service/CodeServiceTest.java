package com.example.demo.service;

import com.example.demo.entity.Code;
import com.example.demo.entity.User;
import com.example.demo.repository.CodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CodeServiceTest {

    @Mock
    CodeRepository codeRepository;

    @InjectMocks
    CodeService codeService;

    @Test
    public void  should_add_code(User user){
        codeService.add(user);
        Mockito.verify(codeRepository,Mockito.times(1)).save(Mockito.any(Code.class));
    }
}
