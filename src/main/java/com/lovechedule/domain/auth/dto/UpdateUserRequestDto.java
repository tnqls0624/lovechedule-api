package com.lovechedule.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(max = 10, message = "이름은 최대 10자까지 입력 가능합니다.")
    private String name;

    @Size(max = 300, message = "FCM 토큰은 최대 300자까지 입력 가능합니다.")
    private String fcm_token;

    private boolean push_enabled;
    private boolean schedule_alarm;
    private boolean anniversary_alarm;
}
