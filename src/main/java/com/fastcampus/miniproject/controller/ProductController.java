package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.config.util.SecurityUtil;
import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.request.ProductRequestDto;
import com.fastcampus.miniproject.dto.response.MemberResponseDto;
import com.fastcampus.miniproject.dto.response.ProductResponseDto;
import com.fastcampus.miniproject.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    @ApiOperation(value = "전체 상품 목록 또는 검색과 필터링", notes = "/products는 전체 상품을 조회하고\n /products?query=?tag=?tag-content는 사용자가 검색 또는 필터링 조건을 설정했을 경우 해당하는 상품을 조회한다.")
    public ResponseWrapper<List<ProductResponseDto.Product>> getProductList(ProductRequestDto.ProductSearch productSearchRequest) {
        return new ResponseWrapper<>(productService.getProductList(productSearchRequest)).ok();
    }

    @GetMapping("/products/{id}")
    @ApiOperation(value = "상품 상세 정보", notes = "상품의 상세한 정보를 조회한다.")
    public ResponseWrapper<MemberResponseDto.ProductDetail> getProduct(@PathVariable Long id) {
        return new ResponseWrapper<>(productService.getProduct(id)).ok();
    }

    @GetMapping("/products/customized")
    @ApiOperation(value = "맞춤형 상품 목록", notes = "사용자에게 추천할 맞춤형 상품(대출, 적금, 펀드) 목록을 조회한다.")
    public ResponseWrapper<ProductResponseDto.ProductListCustom> getCustomizedProductList() {
        return new ResponseWrapper<>(productService.getProductListCustomized(SecurityUtil.getCurrentMemberId())).ok();
    }

    @GetMapping("/products/wisdom")
    @ApiOperation(value = "현명한 소비 목록", notes = "사용자에게 추천할 현명한 소비 상품(카드, 멤버십) 목록을 조회한다.")
    public ResponseWrapper<ProductResponseDto.ProductListWisdom> getWisdomProductList() {
        return new ResponseWrapper<>(productService.getProductListWisdom(SecurityUtil.getCurrentMemberId())).ok();
    }
}