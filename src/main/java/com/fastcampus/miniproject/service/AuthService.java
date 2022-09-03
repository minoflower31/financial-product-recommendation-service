package com.fastcampus.miniproject.service;

import com.fastcampus.miniproject.common.JoinException;
import com.fastcampus.miniproject.config.auth.TokenProvider;
import com.fastcampus.miniproject.dto.TokenDto;
import com.fastcampus.miniproject.dto.request.JoinMemberRequest;
import com.fastcampus.miniproject.dto.request.MemberRequestDto;
import com.fastcampus.miniproject.dto.request.TokenRequestDto;
import com.fastcampus.miniproject.dto.response.MemberAndTokenResponseDto;
import com.fastcampus.miniproject.dto.response.MemberResponseDto;
import com.fastcampus.miniproject.entity.Member;
import com.fastcampus.miniproject.entity.RefreshToken;
import com.fastcampus.miniproject.repository.MemberRepository;
import com.fastcampus.miniproject.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void join(JoinMemberRequest joinMemberRequest) {
        if (memberRepository.existsByLoginId(joinMemberRequest.getEmail())) {
            throw new JoinException("이미 가입되어 있는 유저입니다");
        }
        Member member = joinMemberRequest.toMember(passwordEncoder);
        MemberResponseDto.of(memberRepository.save(member));
    }
    // TODO 확인
    @Transactional
    public MemberAndTokenResponseDto login(MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4.LoginResponseDto 생성
        String name = authentication.getName();
        long id = Long.parseLong(name);

        Member member = memberRepository.findById(id).orElseThrow();

        MemberAndTokenResponseDto memberAndTokenResponseDto = MemberAndTokenResponseDto.builder().member(member).tokenDto(tokenDto).build();

        // 5. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return memberAndTokenResponseDto;
    }

    @Transactional
    public MemberAndTokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6.LoginResponseDto 생성
        MemberAndTokenResponseDto memberAndTokenResponseDto = getMemberAndTokenResponseDto(authentication, tokenDto);

        // 7. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return memberAndTokenResponseDto;
    }

    private MemberAndTokenResponseDto getMemberAndTokenResponseDto(Authentication authentication, TokenDto tokenDto) {
        String name = authentication.getName();
        long id = Long.parseLong(name);

        Member member = memberRepository.findById(id).orElseThrow();

        MemberAndTokenResponseDto memberAndTokenResponseDto = MemberAndTokenResponseDto.builder().member(member).tokenDto(tokenDto).build();
        return memberAndTokenResponseDto;
    }
}
