package com.fastcampus.miniproject.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
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

    public void changeAll(AdditionalInfo info) {
        this.job = info.job;
        this.interest = info.interest;
        this.realEstate = info.realEstate;
        this.car = info.car;
        this.asset = info.asset;
        this.salary = info.salary;
        this.age = info.age;
    }
}