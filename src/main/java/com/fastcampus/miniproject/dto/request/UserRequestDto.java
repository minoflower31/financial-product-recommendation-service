package com.fastcampus.miniproject.dto.request;

import com.fastcampus.miniproject.entity.Member;
import com.fastcampus.miniproject.enums.Authority;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserRequestDto {
    @Data
    public static class Login {
        private String email;
        private String password;
    }

    @Data
    public static class MemberDetail {
        private String job;
        private List<String> interest;
        private String realEstate;
        private String car;
        private String asset;
        private String salary;
        private String age;
    }

    @Data
    public static class Token {
        private String accessToken;
        private String refreshToken;
    }

    @Data
    public static class UpdateMember {
        private String name;
        private String password;
        private String phoneNumber;
        private String job;
        private List<String> interest;
        private String realEstate;
        private String car;
        private String asset;
        private String salary;
        private String age;
    }
    @Data
    public static class JoinMemberRequest {
        private String email;
        private String password;
        private String name;
        private String phoneNumber;
        @Builder
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
}
