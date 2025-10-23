package com.lovechedule.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLoginRequestDto {

    private String socialType;
    private String accessToken;
    private String email;
    private String name;
    private String birthday;
}
