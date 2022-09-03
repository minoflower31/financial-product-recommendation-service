package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.request.JoinMemberRequest;
import com.fastcampus.miniproject.dto.request.MemberRequestDto;
import com.fastcampus.miniproject.dto.request.TokenRequestDto;
import com.fastcampus.miniproject.dto.response.MemberAndTokenResponseDto;
import com.fastcampus.miniproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
     * @param joinMemberRequest 기본 회원 정보 (email, password, name, phoneNumber)
     * @return 응답객체
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
     * @param memberRequestDto 로그인 회원 정보 (email, password)
     * @return 응답객체
     */
    @PostMapping("/login")
    public ResponseWrapper<MemberAndTokenResponseDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return new ResponseWrapper<>(authService.login(memberRequestDto)).ok();
    }

    /**
     * <p>인증 토큰 재발급 및 회원정보 </p>
     * <p>[input data]</p>
     * <pre>
     *     {
     *         "accessToken" :
     *         "refreshToken" :
     *     }
     * </pre>
     * @param tokenRequestDto
     * @return 응답객체
     */
    @PostMapping("/reissue")
    public ResponseWrapper<MemberAndTokenResponseDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseWrapper<>(authService.reissue(tokenRequestDto)).ok();
    }

    /**
     * <p> 로그아웃 기능 </p>
     * <pre>
     *
     * </pre>
     * @param tokenRequestDto
     * @return
     */
//    @PostMapping(value="/logout")
    @DeleteMapping("/logout")
    public ResponseWrapper<Void> logout(@RequestBody TokenRequestDto tokenRequestDto) {
        authService.logout(tokenRequestDto);
        return new ResponseWrapper<Void>().ok();
    }
}
