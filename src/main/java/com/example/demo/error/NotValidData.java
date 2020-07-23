package com.example.demo.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotValidData extends RuntimeException{
    private String message;
}
