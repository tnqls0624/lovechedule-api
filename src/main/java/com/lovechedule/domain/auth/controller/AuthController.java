package com.lovechedule.domain.auth.controller;

import com.lovechedule.domain.auth.dto.LoginRequestDto;
import com.lovechedule.domain.auth.dto.LoginResponseDto;
import com.lovechedule.domain.auth.dto.SocialLoginRequestDto;
import com.lovechedule.domain.auth.dto.SocialLoginResponseDto;
import com.lovechedule.domain.auth.service.AuthService;
import com.lovechedule.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ApiResponse.ok(authService.login(loginRequestDto));
    }

    // 소셜 로그인
    @Tag(name = "AUTH")
    @Operation(summary = "소셜 로그인")
    @PostMapping("/social-login")
    public ApiResponse<SocialLoginResponseDto> socialLogin(@RequestBody SocialLoginRequestDto socialLoginRequestDto) {
        return ApiResponse.ok(authService.socialLogin(socialLoginRequestDto));
    }

}
