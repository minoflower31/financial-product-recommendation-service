package com.fastcampus.miniproject.entity;

import com.fastcampus.miniproject.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private static final String UNSPECIFIED = "미지정";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private AdditionalInfo additionalInfo;

    //로그인
    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    //회원가입
    public Member(String loginId, String password, String name, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.additionalInfo = defaultAdditionalInfo();
    }

    //상세 정보
    public AdditionalInfo defaultAdditionalInfo() {
        return new AdditionalInfo(UNSPECIFIED, UNSPECIFIED,UNSPECIFIED,UNSPECIFIED,UNSPECIFIED,UNSPECIFIED,UNSPECIFIED);
    }

    //Spring Security를 위한 role 메서드
    public void addRole(Role role) {
        this.role = role;
    }
}
