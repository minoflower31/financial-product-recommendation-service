package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.config.auth.PrincipalDetails;
import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.response.ProductResponse;
import com.fastcampus.miniproject.dto.response.ProductSimpleResponse;
import com.fastcampus.miniproject.service.CartService;
import com.fastcampus.miniproject.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/members/cart")
    @ApiOperation(value = "장바구니 목록", notes = "사용자가 장바구니로 등록한 상품 목록을 조회한다.")
    public ResponseWrapper<List<ProductResponse>> findAll(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return new ResponseWrapper<>(cartService.findMemberId(principalDetails.getMember().getId()))
                .ok();
    }

    @PostMapping("/members/cart")
    @ApiOperation(value = "장바구니 등록", notes = "장바구니를 등록한다.")
    public ResponseWrapper<ProductSimpleResponse> add(@RequestBody Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        cartService.add(id, principalDetails.getMember().getId());
        return new ResponseWrapper<>(productService.findByDto(id))
                .ok();
    }

    @DeleteMapping("/members/cart")
    @ApiOperation(value = "장바구니 삭제", notes = "장바구니를 삭제한다.")
    public ResponseWrapper<ProductSimpleResponse> delete(
            @RequestBody Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){

        cartService.delete(id, principalDetails.getMember().getId());
        return new ResponseWrapper<>(productService.findByDto(id))
                .ok();
    }
}
