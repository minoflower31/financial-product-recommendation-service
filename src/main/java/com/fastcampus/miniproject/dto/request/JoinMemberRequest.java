package com.fastcampus.miniproject.dto.request;

import com.fastcampus.miniproject.entity.Member;
import com.fastcampus.miniproject.enums.Authority;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class JoinMemberRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .loginId(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phoneNumber(phoneNumber)
                .authority(Authority.ROLE_USER)
                .build();
    }
}