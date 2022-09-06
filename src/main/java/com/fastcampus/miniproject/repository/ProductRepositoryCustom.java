package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.dto.request.ProductRequestDto;
import com.fastcampus.miniproject.entity.AdditionalInfo;
import com.fastcampus.miniproject.entity.Product;
import com.fastcampus.miniproject.enums.Tag;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findBySearchCond(ProductRequestDto.ProductSearch productSearchRequest);
    List<Product> findCustomizedList(Tag tag, AdditionalInfo additionalInfo);
    List<Product> findWisdomList(Tag tag, AdditionalInfo additionalInfo);

}
