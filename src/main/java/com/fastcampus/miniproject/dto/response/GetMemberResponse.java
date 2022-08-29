package com.fastcampus.miniproject.dto.response;

import com.fastcampus.miniproject.entity.AdditionalInfo;
import lombok.Data;

import java.util.List;

@Data
public class GetMemberResponse {

    private String name;
    private String job;
    private List<String> interest;
    private String realEstate;
    private String car;
    private String asset;
    private String salary;
    private String age;

    public GetMemberResponse(String name, AdditionalInfo info) {
        this.name = name;
        job = info.getJob();
        interest = List.of(info.getInterest().split("\\|"));
        realEstate = info.getRealEstate();
        car = info.getCar();
        asset = info.getAsset();
        salary = info.getSalary();
        age = info.getAge();
    }
}
