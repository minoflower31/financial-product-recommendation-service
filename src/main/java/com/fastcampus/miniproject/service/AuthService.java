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

    @Transactional
    public MemberAndTokenResponseDto login(MemberRequestDto memberRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        String name = authentication.getName();
        long id = Long.parseLong(name);

        Member member = memberRepository.findById(id).orElseThrow();

        MemberAndTokenResponseDto memberAndTokenResponseDto = MemberAndTokenResponseDto.builder().member(member).tokenDto(tokenDto).build();

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        return memberAndTokenResponseDto;
    }

    @Transactional
    public MemberAndTokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        MemberAndTokenResponseDto memberAndTokenResponseDto = getMemberAndTokenResponseDto(authentication, tokenDto);

        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return memberAndTokenResponseDto;
    }

    @Transactional
    public void logout(TokenRequestDto tokenRequestDto) {
        if (!tokenProvider.validateToken(tokenRequestDto.getAccessToken())) {
            throw new RuntimeException("Access Token 이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("이미 로그아웃 된 사용자입니다."));

        refreshTokenRepository.delete(refreshToken);
    }

    private MemberAndTokenResponseDto getMemberAndTokenResponseDto(Authentication authentication, TokenDto tokenDto) {
        String name = authentication.getName();
        long id = Long.parseLong(name);

        Member member = memberRepository.findById(id).orElseThrow();

        return MemberAndTokenResponseDto.builder()
                .member(member)
                .tokenDto(tokenDto)
                .build();
    }
}
