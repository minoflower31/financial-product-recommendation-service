package com.fastcampus.miniproject.entity;

import com.fastcampus.miniproject.enums.Authority;
import lombok.AccessLevel;
import lombok.Builder;
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
    private Authority authority;

    @Embedded
    private AdditionalInfo additionalInfo;

    private Boolean isNotFirst;

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public Member(String loginId, String password, String name, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.authority = Authority.ROLE_USER;
        this.isNotFirst = false;
        this.additionalInfo = defaultAdditionalInfo();
    }

    @Builder
    public Member(String loginId, String password, String name, String phoneNumber, Authority authority) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.authority = authority;
        this.isNotFirst = false;
        this.additionalInfo = defaultAdditionalInfo();
    }

    AdditionalInfo defaultAdditionalInfo() {
        return new AdditionalInfo(UNSPECIFIED, UNSPECIFIED,UNSPECIFIED,UNSPECIFIED,UNSPECIFIED,UNSPECIFIED,UNSPECIFIED);
    }

    public void addRole(Authority authority) {
        this.authority = authority;
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

    public void changeIsNotFirst(Boolean isNotFirst) {
        this.isNotFirst = isNotFirst;
    }
}
