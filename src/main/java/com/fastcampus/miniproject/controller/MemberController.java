package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.config.auth.PrincipalDetails;
import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.request.JoinMemberRequest;
import com.fastcampus.miniproject.dto.request.MemberDetailRequest;
import com.fastcampus.miniproject.dto.request.UpdateMemberRequest;
import com.fastcampus.miniproject.dto.response.GetMemberResponse;
import com.fastcampus.miniproject.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    @ApiOperation(value = "로그인", notes = "")
    public ResponseWrapper<Void> join(@RequestBody JoinMemberRequest joinMemberRequest) {
        System.out.println(joinMemberRequest);
        memberService.joinMember(joinMemberRequest);
        return new ResponseWrapper<Void>().ok();
    }

    @PostMapping("/join/detail")
    @ApiOperation(value = "세부정보 입력", notes = "회원이 세부정보를 입력한다.")
   public ResponseWrapper<Void> inputDetails(@RequestBody MemberDetailRequest request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        memberService.inputDetails(principalDetails.getMember().getId(), request);
        return new ResponseWrapper<Void>().ok();
    }

    @GetMapping("/join/detail-skip")
    @ApiOperation(value = "세부정보 입력할 때 건너띄기", notes = "세부정보를 입력하지 않고 건너띄기 버튼을 클릭했을 때 호출된다.")
    public ResponseWrapper<Void> inputDetailsSkip() {
        return new ResponseWrapper<Void>().noContent();
    }

    @GetMapping("/members")
    @ApiOperation(value = "회원 정보 상세", notes = "회원의 이름과 세부정보를 입력한 데이터가 조회된다.")
    public ResponseWrapper<GetMemberResponse> getMember(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return new ResponseWrapper<>(memberService.getMember(principalDetails.getMember().getId())).ok();
    }

    @PatchMapping("/members")
    @ApiOperation(value = "회원 정보 수정", notes = "회원의 패스워드와 세부정보를 수정할 수 있다.")
    public ResponseWrapper<Void> updateMember(@RequestBody UpdateMemberRequest request,@AuthenticationPrincipal PrincipalDetails principalDetails) {
        memberService.updateJoinInfo(principalDetails.getMember().getId(), request);
        memberService.updateAdditionalInfo(principalDetails.getMember().getId(), request);
        return new ResponseWrapper<Void>().ok();
    }
}