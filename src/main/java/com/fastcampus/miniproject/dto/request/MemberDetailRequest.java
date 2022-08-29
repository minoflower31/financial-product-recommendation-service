package com.fastcampus.miniproject.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class MemberDetailRequest {

    private String job;
    private List<String> interest;
    private String realEstate;
    private String car;
    private String asset;
    private String salary;
    private String age;
}
