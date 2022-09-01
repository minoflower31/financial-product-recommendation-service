package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.TokenDto;
import com.fastcampus.miniproject.dto.request.JoinMemberRequest;
import com.fastcampus.miniproject.dto.request.MemberRequestDto;
import com.fastcampus.miniproject.dto.request.TokenRequestDto;
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

    @PostMapping("/join")
    public ResponseWrapper<Void> join(@RequestBody JoinMemberRequest joinMemberRequest) {
        authService.join(joinMemberRequest);
        return new ResponseWrapper<Void>().ok();
    }

    @PostMapping("/login")
    public ResponseWrapper<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {

        return new ResponseWrapper<TokenDto>(authService.login(memberRequestDto)).ok();
    }

    @PostMapping("/reissue")
    public ResponseWrapper<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseWrapper<>(authService.reissue(tokenRequestDto)).ok();
    }

    @GetMapping("/logout")
    public ResponseWrapper<Void> logout() {

        return new ResponseWrapper<Void>().ok();
    }
}
