package com.fastcampus.miniproject.service;

import com.fastcampus.miniproject.common.JoinException;
import com.fastcampus.miniproject.dto.request.JoinMemberRequest;
import com.fastcampus.miniproject.dto.request.MemberDetailRequest;
import com.fastcampus.miniproject.dto.response.GetMemberResponse;
import com.fastcampus.miniproject.dto.request.UpdateMemberRequest;
import com.fastcampus.miniproject.entity.Member;
import com.fastcampus.miniproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Transactional
    public void joinMember(JoinMemberRequest joinMemberRequest) {
        Optional<Member> targetMember = memberRepository.findMemberByLoginId(joinMemberRequest.getEmail());
        if (targetMember.isPresent()) {
            throw new JoinException("이미 가입되어 있는 유저입니다.");
        }
        String encodingPassword = encodingPassword(joinMemberRequest.getPassword());
        joinMemberRequest.setPassword(encodingPassword);
        memberRepository.save(joinMemberRequest.toMember());
    }

    public GetMemberResponse getMember(Long memberId) {
        Member member = findById(memberId);
        return new GetMemberResponse(member.getName(), member.getAdditionalInfo());
    }

    Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
    }

    private String encodingPassword(String password) {

        String encodingPassword = bCryptPasswordEncoder.encode(password);
        return encodingPassword;
    }

}
