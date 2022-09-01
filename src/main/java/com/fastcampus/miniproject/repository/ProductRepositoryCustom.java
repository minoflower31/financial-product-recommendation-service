package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.dto.request.ProductSearchRequest;
import com.fastcampus.miniproject.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findBySearchCond(ProductSearchRequest productSearchRequest);

}
