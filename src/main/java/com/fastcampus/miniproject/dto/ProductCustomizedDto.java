package com.fastcampus.miniproject.dto;

import com.fastcampus.miniproject.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductCustomizedDto {

    private Long id;
    private String tag;
    private List<String> details;
    private String logo;
    private String companyName;
    private String productName;
    private String price;
    private List<String> age;
    private List<String> job;
    private List<String> realEstate;
    private String car;
    private List<String> asset;
    private String salary;

    public ProductCustomizedDto(Product product) {
        id = product.getId();
        tag = product.getTag();
        details = List.of(product.getDetails().split("\\|"));
        logo = product.getLogo();
        companyName = product.getCompanyName();
        productName = product.getProductName();
        price = product.getPrice();
        age = List.of(product.getAdditionalInfo().getAge().split("\\|"));
        job = List.of(product.getAdditionalInfo().getJob().split("\\|"));
        realEstate = List.of(product.getAdditionalInfo().getRealEstate().split("\\|"));
        car = product.getAdditionalInfo().getCar();
        asset = List.of(product.getAdditionalInfo().getAsset().split("\\|"));
        salary = product.getAdditionalInfo().getSalary();
    }
}