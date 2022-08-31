package com.fastcampus.miniproject.dto.request;

import com.fastcampus.miniproject.entity.Member;
import lombok.Data;

@Data
public class JoinMemberRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    public Member toMember() {
        return new Member(email, password, name, phoneNumber);
    }
}
