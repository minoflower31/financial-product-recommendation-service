package com.fastcampus.miniproject.service;

import com.fastcampus.miniproject.dto.*;
import com.fastcampus.miniproject.dto.response.*;
import com.fastcampus.miniproject.entity.Product;
import com.fastcampus.miniproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.fastcampus.miniproject.enums.Tag.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getProductList() {
        return productRepository.findAll().stream()
                .map(ProductResponse::new)
                .toList();
    }

    public List<ProductResponse> getProductList(String query, String tag, String tagContent) {

        return productRepository.findAll().stream()
                .map(ProductResponse::new)
                .toList();
    }

    public ProductDetailResponse getProduct(Long productId) {
        return new ProductDetailResponse(findById(productId));
    }

    public ProductListCustomizedResponse getProductListCustomized() {
        List<ProductCustomizedDto> loan = productRepository.findAllByTag(LOAN.getValue()).stream()
                .map(ProductCustomizedDto::new)
                .toList();

        List<ProductCustomizedDto> fund = productRepository.findAllByTag(FUND.getValue()).stream()
                .map(ProductCustomizedDto::new)
                .toList();

        List<ProductCustomizedDto> savings = productRepository.findAllByTag(SAVING.getValue()).stream()
                .map(ProductCustomizedDto::new)
                .toList();

        return new ProductListCustomizedResponse(loan, fund, savings);
    }

    public ProductListWisdomResponse getProductListWisdom() {
        List<ProductWisdomDto> cards = productRepository.findAllByTag(CARD.getValue()).stream()
                .map(ProductWisdomDto::new)
                .toList();

        List<ProductWisdomDto> memberships = productRepository.findAllByTag(SAVING.getValue()).stream()
                .map(ProductWisdomDto::new)
                .collect(Collectors.toList());

        return new ProductListWisdomResponse(cards, memberships);
    }

    public ProductSimpleResponse findByDto(Long productId) {
        return productRepository.findById(productId)
                .map(ProductSimpleResponse::new)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다."));
    }

    Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}