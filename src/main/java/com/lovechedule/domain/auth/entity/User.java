package com.lovechedule.domain.auth.entity;// User.java

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.Instant;

@Document(collection = "users")
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    private String id;

    @NotBlank
    @Email
    @Indexed(unique = true)                 // unique: true
    private String email;

    @NotBlank
    private String name;

    // EMAIL 로그인 타입에서만 사용 (nullable)
    private String password;

    @Builder.Default
    private SocialType loginType = SocialType.EMAIL;  // default: EMAIL

    private String gender;       // optional
    private String birthday;     // optional(문자열로 동일 대응)

    @NotBlank
    @Indexed                         // index: true
    private String inviteCode;

    @Indexed                         // index: true
    private String fcmToken;

    @Builder.Default
    private boolean pushEnabled = true;

    @Builder.Default
    private boolean scheduleAlarm = true;

    @Builder.Default
    private boolean anniversaryAlarm = true;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
