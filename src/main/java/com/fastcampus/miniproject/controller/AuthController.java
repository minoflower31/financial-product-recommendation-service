package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.TokenDto;
import com.fastcampus.miniproject.dto.request.JoinMemberRequest;
import com.fastcampus.miniproject.dto.request.MemberRequestDto;
import com.fastcampus.miniproject.dto.request.TokenRequestDto;
import com.fastcampus.miniproject.dto.response.LoginResponseDto;
import com.fastcampus.miniproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * <p> 회원가입 기능 </p>
     * <p>[input data]</p>
     * <pre>
     *     {
     *         "email" :
     *         "password" :
     *         "name" :
     *         "phoneNumber" :
     *     }
     * </pre>
     * @param joinMemberRequest 기본 회원 정보
     * @return 상태코드
     */
    @PostMapping("/join")
    public ResponseWrapper<Void> join(@RequestBody JoinMemberRequest joinMemberRequest) {
        authService.join(joinMemberRequest);
        return new ResponseWrapper<Void>().ok();
    }

    /**
     * <p> 로그인 기능 </p>
     * <p>[input data]</p>
     * <pre>
     *     {
     *         "email" :
     *         "password" :
     *     }
     * </pre>
     * @param memberRequestDto 로그인 회원 정보
     * @return 상태코드
     */
    @PostMapping("/login")
    public ResponseWrapper<LoginResponseDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return new ResponseWrapper<>(authService.login(memberRequestDto)).ok();
    }

    /**
     * <p>인증 토큰 재발급</p>
     * <p>[input data]</p>
     * <pre>
     *     {
     *         "accessToken" :
     *         "refreshToken" :
     *     }
     * </pre>
     * @param tokenRequestDto
     * @return
     */
    @PostMapping("/reissue")
    public ResponseWrapper<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseWrapper<>(authService.reissue(tokenRequestDto)).ok();
    }
    // TODO 로그아웃 기능 추가
    @GetMapping("/logout")
    public ResponseWrapper<Void> logout() {
        return new ResponseWrapper<Void>().ok();
    }
}
