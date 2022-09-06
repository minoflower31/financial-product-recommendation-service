package com.fastcampus.miniproject.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column
    private String companyName;
    private String productName;
    private String price;
    private String tag;
    private String tagContent;
    private String details;
    private String logo;
    private String description;

    @Embedded
    private AdditionalInfo additionalInfo;
}
