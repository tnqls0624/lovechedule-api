package com.lovechedule.domain.auth.entity;// User.java

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.Instant;
import java.util.Date;

@Document(collection = "users")
@Data
@Builder
public class User {

    @Id
    private String id;

    @NotBlank
    private String login_type;

    @NotBlank
    @Email
    @Indexed(unique = true)                 // unique: true
    private String email;

    @NotBlank
    private String name;

    // EMAIL 로그인 타입에서만 사용 (nullable)
    private String password;

    private String gender;       // optional

    private String birthday;     // optional(문자열로 동일 대응)

    @NotBlank
    @Indexed                         // index: true
    private String invite_code;

    private String thumbnail_image;

    @Indexed                         // index: true
    private String fcmToken;

    @Builder.Default
    private boolean push_enabled = true;

    @Builder.Default
    private boolean schedule_alarm = true;

    @Builder.Default
    private boolean anniversary_alarm = true;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
