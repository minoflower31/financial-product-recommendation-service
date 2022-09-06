package com.fastcampus.miniproject.service;

import com.fastcampus.miniproject.dto.request.UserRequestDto;
import com.fastcampus.miniproject.dto.response.MemberResponseDto;
import com.fastcampus.miniproject.entity.AdditionalInfo;
import com.fastcampus.miniproject.entity.Member;
import com.fastcampus.miniproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void inputDetails(Long memberId, UserRequestDto.MemberDetail request) {
        Member member = findById(memberId);

        updateIsNotFirst(member.getId());
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
    public void updateJoinInfo(Long memberId, UserRequestDto.UpdateMember request) {
        Member member = findById(memberId);
        if (request.getPassword() != null)
            member.changePassword(request.getPassword());

        if(request.getName() != null)
            member.changeName(request.getName());

        if(request.getPhoneNumber() != null)
            member.changePhoneNumber(request.getPhoneNumber());
    }

    @Transactional
    public void updateAdditionalInfo(Long memberId, UserRequestDto.UpdateMember request) {
        Member member = findById(memberId);
        AdditionalInfo info = member.getAdditionalInfo();

        member.changeAdditionalInfo(
                (request.getJob() == null) ? info.getJob() : request.getJob(),
                (request.getInterest() == null) ? List.of(info.getInterest().split("\\|")) : request.getInterest(),
                (request.getRealEstate() == null) ? info.getRealEstate() : request.getRealEstate(),
                (request.getCar() == null) ? info.getCar() : request.getCar(),
                (request.getAsset() == null) ? info.getAsset() : request.getAsset(),
                (request.getSalary() == null) ? info.getSalary() : request.getSalary(),
                (request.getAge() == null) ? info.getAge() : request.getAge());
    }

    @Transactional
    public void updateIsNotFirst(Long memberId) {
        Member member = findById(memberId);
        member.changeIsNotFirst(true);
    }

    public MemberResponseDto.GetMember getMember(Long memberId) {
        Member member = findById(memberId);
        return new MemberResponseDto.GetMember(member.getName(), member.getAdditionalInfo());
    }

    Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
    }
}
