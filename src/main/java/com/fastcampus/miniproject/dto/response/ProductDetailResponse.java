package com.fastcampus.miniproject.dto.response;

import com.fastcampus.miniproject.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailResponse {

    private Long id;
    private String companyName;
    private String productName;
    private String description;
    private String tag;
    private List<String> tagContent;
    private List<String> details;
    private String logo;
    private String price;
    private List<String> age;
    private List<String> job;

    public ProductDetailResponse(Product product) {
        id = product.getId();
        companyName = product.getCompanyName();
        productName = product.getProductName();
        description = product.getDescription();
        tag = product.getTag();
        tagContent = List.of(product.getTagContent().split("\\|"));
        details = List.of(product.getDetails().split("\\|"));
        logo = product.getLogo();
        price = product.getPrice();
        age = List.of(product.getAdditionalInfo().getAge().split("\\|"));
        job = List.of(product.getAdditionalInfo().getJob().split("\\|"));
    }
}