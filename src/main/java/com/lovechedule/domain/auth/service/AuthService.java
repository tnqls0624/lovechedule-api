package com.lovechedule.domain.auth.service;

import com.lovechedule.domain.auth.dto.LoginRequestDto;
import com.lovechedule.domain.auth.dto.LoginResponseDto;
import com.lovechedule.domain.auth.dto.SocialLoginRequestDto;
import com.lovechedule.domain.auth.dto.SocialLoginResponseDto;
import com.lovechedule.domain.auth.entity.SocialType;
import com.lovechedule.domain.auth.entity.User;
import com.lovechedule.domain.auth.repository.UserRepository;
import com.lovechedule.global.BusinessException;
import com.lovechedule.global.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;



@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    // 이메일 로그인
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        return LoginResponseDto.builder().accessToken("accessToken").build();
    }

    // 초대코드 생성
    private String generateInviteCode() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int length = 6;
        return RandomStringUtils.random(length, 0, chars.length, false, false, chars, new SecureRandom());
    }

    public SocialLoginResponseDto socialLogin(SocialLoginRequestDto socialLoginRequestDto) {

        String inviteCode = generateInviteCode();

        switch (socialLoginRequestDto.getSocialType()) {
            case SocialType.EMAIL -> {
                // 이메일 로그인 처리 로직
                System.out.println("이메일 로그인 시도");
                if(userRepository.existsByEmail(socialLoginRequestDto.getEmail())) {
                    throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
                }

            }
            case SocialType.KAKAO -> {
                System.out.println("카카오 로그인 시도");

                // kakao api 요청


                // 1) 사용자 생성
//                User user = userRepository.save(
//                        User.builder()
//                                .email(req.getUsername())
//                                .password(passwordEncoder.encode(req.getPassword()))
//                                .email(req.getEmail())
//                                .status(1)
//                                .build()
//                );
            }
        }


        return SocialLoginResponseDto.builder().accessToken("").build();
    }


}
