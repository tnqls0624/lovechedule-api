package com.lovechedule.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class UserResponseDto {

    private String id;
    private String login_type;
    private String email;
    private String name;
    private String thumbnail_image;
    private String gender;
    private String birthday;
    private String invite_code;
    private String fcmToken;
    private boolean push_enabled;
    private boolean schedule_alarm;
    private boolean anniversary_alarm;
    private Date createdAt;
    private Date updatedAt;

}