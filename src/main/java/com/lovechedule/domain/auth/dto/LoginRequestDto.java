package com.lovechedule.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class LoginRequestDto {
    private String email;
    private String password;
}