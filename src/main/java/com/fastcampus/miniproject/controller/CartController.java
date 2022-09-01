package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.config.util.SecurityUtil;
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
    public ResponseWrapper<List<ProductResponse>> findAll() {
        return new ResponseWrapper<>(cartService.findMemberId(SecurityUtil.getCurrentMemberId()))
                .ok();
    }

    @PostMapping("/members/cart")
    public ResponseWrapper<ProductSimpleResponse> add(@RequestBody Long id) {

        cartService.add(id, SecurityUtil.getCurrentMemberId());

        return new ResponseWrapper<>(productService.findByDto(id))
                .ok();
    }

    @DeleteMapping("/members/cart")
    public ResponseWrapper<ProductSimpleResponse> delete(@RequestBody Long id){

        cartService.delete(id, SecurityUtil.getCurrentMemberId());

        return new ResponseWrapper<>(productService.findByDto(id)).ok();
    }

}
