package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.request.JoinMemberRequest;
import com.fastcampus.miniproject.dto.request.MemberDetailRequest;
import com.fastcampus.miniproject.dto.request.UpdateMemberRequest;
import com.fastcampus.miniproject.dto.response.GetMemberResponse;
import com.fastcampus.miniproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private static Long MEMBER_ID = 1L;

    @PostMapping("/join")
    public ResponseWrapper<Void> join(@RequestBody JoinMemberRequest joinMemberRequest) {
        System.out.println(joinMemberRequest);
        memberService.joinMember(joinMemberRequest);
        return new ResponseWrapper<Void>().ok();
    }

    @PostMapping("/join/detail")
    public ResponseWrapper<Void> inputDetails(@RequestBody MemberDetailRequest request) {
        memberService.inputDetails(MEMBER_ID, request);
        return new ResponseWrapper<Void>().ok();
    }

    @GetMapping("/join/detail-skip")
    public ResponseWrapper<Void> inputDetailsSkip() {
        return new ResponseWrapper<Void>().noContent();
    }

    @GetMapping("/members")
    public ResponseWrapper<GetMemberResponse> getMember() {
        return new ResponseWrapper<>(memberService.getMember(MEMBER_ID)).ok();
    }

    @PatchMapping("/members")
    public ResponseWrapper<Void> updatePasswordAdditionalInfo(@RequestBody UpdateMemberRequest request) {
        memberService.updatePasswordAdditionalInfo(MEMBER_ID, request);
        return new ResponseWrapper<Void>().ok();
    }
}