package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.response.ProductResponse;
import com.fastcampus.miniproject.dto.response.ProductSimpleResponse;
import com.fastcampus.miniproject.service.CartService;
import com.fastcampus.miniproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/members/cart")
    public ResponseWrapper<List<ProductResponse>> findAll(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return new ResponseWrapper<>(cartService.findMemberId(principalDetails.getId()))
                .ok();
    }

    @PostMapping("/members/cart")
    public ResponseWrapper<ProductSimpleResponse> add(@RequestBody Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        cartService.add(id, principalDetails.getId());

        return new ResponseWrapper<>(productService.findByDto(id))
                .ok();
    }

    @DeleteMapping("/members/cart")
    public ResponseWrapper<ProductSimpleResponse> delete(
            @RequestBody Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){

        cartService.delete(id, principalDetails.getId());
        return new ResponseWrapper<>(productService.findByDto(id))
                .ok();
    }

}
