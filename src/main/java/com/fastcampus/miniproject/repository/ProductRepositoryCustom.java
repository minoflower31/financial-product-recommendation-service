package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.dto.request.ProductSearchRequest;
import com.fastcampus.miniproject.entity.AdditionalInfo;
import com.fastcampus.miniproject.entity.Product;
import com.fastcampus.miniproject.enums.Tag;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findBySearchCond(ProductSearchRequest productSearchRequest);

    List<Product> findCustomizedList(Tag tag, AdditionalInfo additionalInfo);

    List<Product> findWisdomList(Tag tag, AdditionalInfo additionalInfo);
}