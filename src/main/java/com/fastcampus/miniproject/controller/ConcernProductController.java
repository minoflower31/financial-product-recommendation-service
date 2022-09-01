package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.config.util.SecurityUtil;
import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.response.ProductResponse;
import com.fastcampus.miniproject.dto.response.ProductSimpleResponse;
import com.fastcampus.miniproject.service.ConcernProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConcernProductController {

    private final ConcernProductService concernProductService;

    @GetMapping("/products/concern")
    public ResponseWrapper<List<ProductResponse>> findAll() {
        return new ResponseWrapper<>(concernProductService.findAllById(SecurityUtil.getCurrentMemberId())).ok();
    }

    @PostMapping("/products/concern")
    public ResponseWrapper<ProductSimpleResponse> register(@RequestBody Long id) {

        concernProductService.register(id, SecurityUtil.getCurrentMemberId());

        return new ResponseWrapper<>(concernProductService.findById(id)).ok();
}

    @DeleteMapping("/products/concern")
    public ResponseWrapper<ProductSimpleResponse> delete(@RequestBody Long id){
        concernProductService.delete(id, SecurityUtil.getCurrentMemberId());

        return new ResponseWrapper<>(concernProductService.findById(id)).ok();
    }
}
