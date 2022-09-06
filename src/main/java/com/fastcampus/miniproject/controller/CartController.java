package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.config.util.SecurityUtil;
import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.response.ProductResponse;
import com.fastcampus.miniproject.dto.response.ProductSimpleResponse;
import com.fastcampus.miniproject.service.CartService;
import com.fastcampus.miniproject.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/members/cart")
    @ApiOperation(value = "장바구니 목록", notes = "사용자가 장바구니로 등록한 상품 목록을 조회한다.")
    public ResponseWrapper<List<ProductResponse>> findAll() {
        return new ResponseWrapper<>(cartService.findMemberId(SecurityUtil.getCurrentMemberId()))
                .ok();
    }

    @PostMapping("/members/cart")
    @ApiOperation(value = "장바구니 등록", notes = "장바구니를 등록한다.")
    public ResponseWrapper<ProductSimpleResponse> add(@RequestBody CartRequest request) {

        cartService.add(request.getId(), SecurityUtil.getCurrentMemberId());

        return new ResponseWrapper<>(productService.findByDto(request.getId()))
                .ok();
    }

    @DeleteMapping("/members/cart")
    @ApiOperation(value = "장바구니 삭제", notes = "장바구니를 삭제한다.")
    public ResponseWrapper<ProductSimpleResponse> delete(@RequestBody CartRequest request){

        cartService.delete(request.getId(), SecurityUtil.getCurrentMemberId());

        return new ResponseWrapper<>(productService.findByDto(request.getId())).ok();
    }

    @Data
    static class CartRequest {

        private Long id;
    }
}
