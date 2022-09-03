package com.fastcampus.miniproject.service;

import com.fastcampus.miniproject.dto.ProductCustomizedDto;
import com.fastcampus.miniproject.dto.ProductWisdomDto;
import com.fastcampus.miniproject.dto.request.ProductSearchRequest;
import com.fastcampus.miniproject.dto.response.*;
import com.fastcampus.miniproject.entity.AdditionalInfo;
import com.fastcampus.miniproject.entity.Product;
import com.fastcampus.miniproject.repository.ProductRepository;
import com.fastcampus.miniproject.repository.ProductRepositoryImpl;
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
    private final ProductRepositoryImpl queryRepository;
    private final MemberService memberService;

    public List<ProductResponse> getProductList(ProductSearchRequest productSearchRequest) {

        if (productSearchRequest.isFieldsNull()) {
            return productRepository.findAll()
                    .stream()
                    .map(ProductResponse::new)
                    .collect(Collectors.toList());
        }
        return queryRepository.findBySearchCond(productSearchRequest)
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    public ProductDetailResponse getProduct(Long productId) {
        return new ProductDetailResponse(findById(productId));
    }

    public ProductListCustomizedResponse getProductListCustomized(Long memberId) {

        AdditionalInfo additionalInfo = memberService.findById(memberId)
                .getAdditionalInfo();

        List<ProductCustomizedDto> loan = mapToDto(queryRepository.findCustomizedList(LOAN, additionalInfo));
        List<ProductCustomizedDto> fund = mapToDto(queryRepository.findCustomizedList(FUND, additionalInfo));
        List<ProductCustomizedDto> savings = mapToDto(queryRepository.findCustomizedList(SAVING, additionalInfo));

        return new ProductListCustomizedResponse(loan, fund, savings);
    }

    public ProductListWisdomResponse getProductListWisdom(Long memberId) {

        AdditionalInfo additionalInfo = memberService.findById(memberId)
                .getAdditionalInfo();

        List<ProductWisdomDto> cards = queryRepository.findWisdomList(CARD, additionalInfo)
                .stream()
                .map(ProductWisdomDto::new)
                .toList();

        List<ProductWisdomDto> memberships = queryRepository.findWisdomList(SAVING, additionalInfo)
                .stream()
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

    private List<ProductCustomizedDto> mapToDto(List<Product> productList) {
        return productList
                .stream()
                .map(ProductCustomizedDto::new)
                .toList();
    }
}