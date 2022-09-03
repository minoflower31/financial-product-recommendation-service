package com.fastcampus.miniproject.dto.response;

import com.fastcampus.miniproject.dto.TokenDto;
import com.fastcampus.miniproject.entity.Member;
import lombok.Builder;
import lombok.Data;



@Data
public class LoginResponseDto {
    private String email;
    private String name;
    private String phoneNumber;
    private Boolean isNotFirst;
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginResponseDto(Member member, TokenDto tokenDto) {
        this.email = member.getLoginId();
        this.name = member.getName();
        this.phoneNumber = member.getPhoneNumber();
        this.isNotFirst = member.getIsNotFirst();
        this.accessToken = tokenDto.getAccessToken();
        this.refreshToken = tokenDto.getRefreshToken();
    }
}
