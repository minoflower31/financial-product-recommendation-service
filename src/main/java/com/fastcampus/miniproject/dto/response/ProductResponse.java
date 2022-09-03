package com.fastcampus.miniproject.dto.response;

import com.fastcampus.miniproject.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private Long id;
    private String tag;
    private String description;
    private String logo;
    private String companyName;
    private String productName;
    private String price;

    public ProductResponse(Product product) {
        id = product.getId();
        tag = product.getTag();
        description = product.getDescription();
        logo = product.getLogo();
        companyName = product.getCompanyName();
        productName = product.getProductName();
        price = product.getPrice();
    }
}