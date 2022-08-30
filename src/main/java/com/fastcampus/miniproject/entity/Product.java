package com.fastcampus.miniproject.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;

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
