package com.lovechedule.domain.auth.controller;

import com.lovechedule.domain.auth.dto.*;
import com.lovechedule.domain.auth.service.AuthService;
import com.lovechedule.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 이메일 로그인
    @Tag(name = "AUTH")
    @Operation(summary = "이메일 로그인")
    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ApiResponse.ok(authService.login(loginRequestDto));
    }

    // 소셜 로그인
    @Tag(name = "AUTH")
    @Operation(summary = "소셜 로그인")
    @PostMapping("/social-login")
    public ApiResponse<SocialLoginResponseDto> socialLogin(@Valid @RequestBody SocialLoginRequestDto socialLoginRequestDto) {
        return ApiResponse.ok(authService.socialLogin(socialLoginRequestDto));
    }

    // 개인 정보 수정
    @Tag(name= "AUTH")
    @Operation(summary = "개인 정보 수정")
    @PostMapping("/update-user-info")
    public void updateUserInfo(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("userDetails = " + userDetails.getUsername());
//        return ApiResponse.ok(authService.updateUserInfo(updateUserRequestDto));
    }

}
