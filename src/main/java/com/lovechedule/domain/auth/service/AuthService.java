package com.lovechedule.domain.auth.service;

import com.lovechedule.domain.auth.dto.*;
import com.lovechedule.domain.auth.entity.User;
import com.lovechedule.domain.auth.jwt.JwtTokenProvider;
import com.lovechedule.domain.auth.repository.UserRepository;
import com.lovechedule.global.BusinessException;
import com.lovechedule.global.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import java.security.SecureRandom;

import com.lovechedule.domain.auth.entity.LoginType;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final WebClient webClient;

    // 이메일 로그인
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_LOGIN));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }

        String accessToken = jwtTokenProvider.createToken(user.getEmail());

        return LoginResponseDto.builder().accessToken(accessToken).build();
    }

    // 초대코드 생성
    private String generateInviteCode() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int length = 6;
        return RandomStringUtils.random(length, 0, chars.length, false, false, chars, new SecureRandom());
    }

    public SocialLoginResponseDto socialLogin(SocialLoginRequestDto socialLoginRequestDto) {
        final String inviteCode = generateInviteCode();
        User user = null;

        try {
            switch (LoginType.valueOf(socialLoginRequestDto.getLoginType())) {
                case KAKAO -> user = handleKakaoLogin(socialLoginRequestDto, inviteCode);
                case GOOGLE, FACEBOOK, NAVER, APPLE ->
                        throw new BusinessException(ErrorCode.SOCIAL_LOGIN_FAILED, "아직 지원하지 않는 로그인 타입입니다.");
                default -> throw new BusinessException(ErrorCode.SOCIAL_LOGIN_FAILED);
            }
        } catch (WebClientResponseException e) {
            // HTTP 응답 코드 오류 (401, 400 등)
            log.error("[SocialLogin] 외부 API 오류: {}", e.getResponseBodyAsString(), e);
            throw new BusinessException(ErrorCode.SOCIAL_LOGIN_FAILED, "소셜 서버 통신 오류 (" + e.getStatusCode() + ")");
        } catch (WebClientRequestException e) {
            // 네트워크 오류 (타임아웃, 연결 실패 등)
            log.error("[SocialLogin] 외부 요청 실패", e);
            throw new BusinessException(ErrorCode.SOCIAL_LOGIN_FAILED, "소셜 서버에 연결할 수 없습니다.");
        } catch (Exception e) {
            log.error("[SocialLogin] 처리 중 예외 발생", e);
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "소셜 로그인 처리 중 오류가 발생했습니다.");
        }

        String accessToken = jwtTokenProvider.createToken(user.getEmail());

        return SocialLoginResponseDto.builder().accessToken(accessToken).build();

    }

    private User handleKakaoLogin(SocialLoginRequestDto dto, String inviteCode) {
        final String kakaoApiUrl = "https://kapi.kakao.com/v2/user/me";

        KakaoUserResponseDto kakao = webClient.get()
                .uri(kakaoApiUrl)
                .header("Authorization", "Bearer " + dto.getAccessToken())
                .retrieve()
                .bodyToMono(KakaoUserResponseDto.class)
                .block();

        if (kakao == null || kakao.getEmail() == null) {
            throw new BusinessException(ErrorCode.SOCIAL_LOGIN_FAILED, "카카오 계정 정보를 확인할 수 없습니다.");
        }

        String email = kakao.getEmail();

        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(email)
                        .name(kakao.getName() != null ? kakao.getName() : email)
                        .login_type(dto.getLoginType())
                        .invite_code(inviteCode)
                        .gender(kakao.getGender() != null ? kakao.getGender() : null)
                        .birthday(kakao.getBirthday() != null ? kakao.getBirthday() : null)
                        .build()));
    }

    // 개인 정보 수정
    public void updateUserInfo(UpdateUserRequestDto updateUserRequestDto) {
    }
}
