package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.request.UserRequestDto;
import com.fastcampus.miniproject.dto.response.MemberResponseDto;
import com.fastcampus.miniproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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
     * @param joinMemberRequest 기본 회원 정보 (email, password, name, phoneNumber)
     * @return 응답객체
     */
    @PostMapping("/join")
    public ResponseWrapper<Void> join(@RequestBody UserRequestDto.JoinMemberRequest joinMemberRequest) {
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
     * @param login 로그인 회원 정보 (email, password)
     * @return 응답객체
     */
    @PostMapping("/login")
    public ResponseWrapper<MemberResponseDto.MemberAndToken> login(@RequestBody UserRequestDto.Login login) {
        return new ResponseWrapper<>(authService.login(login)).ok();
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
     * @param token (accessToken, refreshToken)
     * @return 응답객체
     */
    @PostMapping("/reissue")
    public ResponseWrapper<MemberResponseDto.MemberAndToken> reissue(@RequestBody UserRequestDto.Token token ) {
        return new ResponseWrapper<>(authService.reissue(token)).ok();
    }

    /**
     * <p> 로그아웃 기능 </p>
     * <pre>
     *     {
     *         "accessToken" :
     *         "refreshToken" :
     *     }
     * </pre>
     * @param token (accessToken, refreshToken)
     * @return
     */
    @PostMapping("/logout")
    public ResponseWrapper<Void> logout(@RequestBody UserRequestDto.Token token) {
        authService.logout(token);
        return new ResponseWrapper<Void>().ok();
    }
}
