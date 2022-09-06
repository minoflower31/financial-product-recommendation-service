package com.fastcampus.miniproject.dto.response;

import com.fastcampus.miniproject.entity.Product;
import lombok.Data;

@Data
public class ProductSimpleResponse {

    private Long id;
    private String productName;

    public ProductSimpleResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
    }
}
