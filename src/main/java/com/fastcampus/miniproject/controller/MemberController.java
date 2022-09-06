package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.config.util.SecurityUtil;
import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.request.UserRequestDto;
import com.fastcampus.miniproject.dto.response.MemberResponseDto;
import com.fastcampus.miniproject.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @PostMapping("/join/detail")
    @ApiOperation(value = "세부정보 입력", notes = "회원이 세부정보를 입력한다.")
    public ResponseWrapper<Void> inputDetails(@RequestBody UserRequestDto.MemberDetail request) {

        memberService.inputDetails(SecurityUtil.getCurrentMemberId(), request);

        return new ResponseWrapper<Void>()
                .ok();
    }

    @GetMapping("/join/detail-skip")
    @ApiOperation(value = "세부정보 입력할 때 건너띄기", notes = "세부정보를 입력하지 않고 건너띄기 버튼을 클릭했을 때 호출된다.")
    public ResponseWrapper<Void> inputDetailsSkip() {
        memberService.updateIsNotFirst(SecurityUtil.getCurrentMemberId());

        return new ResponseWrapper<Void>().noContent();
    }

    @GetMapping("/members")
    @ApiOperation(value = "회원 정보 상세", notes = "회원의 이름과 세부정보를 입력한 데이터가 조회된다.")
    public ResponseWrapper<MemberResponseDto.GetMember> getMember() {

        return new ResponseWrapper<>(memberService.getMember(SecurityUtil.getCurrentMemberId()))
                .ok();
    }

    @PatchMapping("/members")
    @ApiOperation(value = "회원 정보 수정", notes = "회원의 패스워드와 세부정보를 수정할 수 있다.")
    public ResponseWrapper<Void> updatePasswordAdditionalInfo(@RequestBody UserRequestDto.UpdateMember request) {
        
        memberService.updateJoinInfo(SecurityUtil.getCurrentMemberId(), request);

        memberService.updateAdditionalInfo(SecurityUtil.getCurrentMemberId(), request);
        return new ResponseWrapper<Void>()
                .ok();
    }
}