package com.fastcampus.miniproject.entity;

import com.fastcampus.miniproject.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.buf.StringUtils;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "USERNAME")
    private String name;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLES")
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
        this.role = Role.USER;
        this.additionalInfo = defaultAdditionalInfo();
    }

    //상세 정보
    AdditionalInfo defaultAdditionalInfo() {
        return new AdditionalInfo(UNSPECIFIED, UNSPECIFIED,UNSPECIFIED,UNSPECIFIED,UNSPECIFIED,UNSPECIFIED,UNSPECIFIED);
    }

    //Spring Security를 위한 role 메서드
    public void addRole(Role role) {
        this.role = role;
    }

    public void changeAdditionalInfo(String job, List<String> interest, String realEstate, String car, String asset, String salary, String age) {
        additionalInfo.changeAll(new AdditionalInfo(job, StringUtils.join(interest, '|'), realEstate, car, asset, salary, age));
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePhoneNumber(String phoneNumber) {
        this.password = phoneNumber;
    }
}
