package com.fastcampus.miniproject.dto.request;

import lombok.Data;

@Data
public class LoginMemberRequest {
    private String email;
    private String password;
}