package com.lovechedule.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserResponseDto {

    private String email;
    private String name;
    private String gender;
    private String birthday;
    private String thumbnail_image;
}
