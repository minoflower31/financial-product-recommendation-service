package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.config.util.SecurityUtil;
import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.request.ProductRequestDto;
import com.fastcampus.miniproject.dto.response.ProductResponseDto;
import com.fastcampus.miniproject.service.ConcernProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConcernProductController {

    private final ConcernProductService concernProductService;

    @GetMapping("/products/concern")
    @ApiOperation(value = "관심상품 목록", notes = "사용자가 관심상품으로 등록한 상품 목록을 조회한다.")
    public ResponseWrapper<List<ProductResponseDto.Product>> findAll() {
        return new ResponseWrapper<>(concernProductService.findAllById(SecurityUtil.getCurrentMemberId())).ok();
    }

    @PostMapping("/products/concern")
    @ApiOperation(value = "관심상품 등록", notes = "관심상품을 등록한다.")
    public ResponseWrapper<ProductResponseDto.ProductSimple> register(@RequestBody ProductRequestDto.ConcernProduct request) {

        concernProductService.register(request.getId(), SecurityUtil.getCurrentMemberId());

        return new ResponseWrapper<>(concernProductService.findById(request.getId())).ok();
}

    @DeleteMapping("/products/concern")
    @ApiOperation(value = "관심상품 삭제", notes = "관심상품을 삭제한다.")
    public ResponseWrapper<ProductResponseDto.ProductSimple> delete(@RequestBody ProductRequestDto.ConcernProduct request){
        concernProductService.delete(request.getId(), SecurityUtil.getCurrentMemberId());

        return new ResponseWrapper<>(concernProductService.findById(request.getId())).ok();
    }
}
