package com.fastcampus.miniproject.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdditionalInfo {

    private String job;
    private String interest;
    private String realEstate;
    private String car;
    private String asset;
    private String salary;
    private String age;

    public AdditionalInfo(String job, String interest, String realEstate, String car, String asset, String salary, String age) {
        this.job = job;
        this.interest = interest;
        this.realEstate = realEstate;
        this.car = car;
        this.asset = asset;
        this.salary = salary;
        this.age = age;
    }
}
