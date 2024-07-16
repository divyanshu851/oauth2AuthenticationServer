package com.example.securityproject.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SignUpRequestDto {
    private String email;
    private String password;

}
