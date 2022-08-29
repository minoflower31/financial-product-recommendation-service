package com.fastcampus.miniproject.service;

import com.fastcampus.miniproject.dto.request.MemberDetailRequest;
import com.fastcampus.miniproject.dto.response.GetMemberResponse;
import com.fastcampus.miniproject.dto.request.UpdateMemberRequest;
import com.fastcampus.miniproject.entity.Member;
import com.fastcampus.miniproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void inputDetails(Long memberId, MemberDetailRequest request) {
        Member member = findById(memberId);
        member.changeAdditionalInfo(
                request.getJob(),
                request.getInterest(),
                request.getRealEstate(),
                request.getCar(),
                request.getAsset(),
                request.getSalary(),
                request.getAge());
    }

    @Transactional
    public void updatePasswordAdditionalInfo(Long memberId, UpdateMemberRequest request) {
        Member member = findById(memberId);
        member.changePassword(request.getPassword());
        member.changeAdditionalInfo(
                request.getJob(),
                request.getInterest(),
                request.getRealEstate(),
                request.getCar(),
                request.getAsset(),
                request.getSalary(),
                request.getAge());
    }

    public GetMemberResponse getMember(Long memberId) {
        Member member = findById(memberId);
        return new GetMemberResponse(member.getName(), member.getAdditionalInfo());
    }

    Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
    }
}
