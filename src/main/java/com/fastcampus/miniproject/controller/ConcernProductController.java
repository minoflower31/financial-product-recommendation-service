package com.fastcampus.miniproject.controller;

import com.fastcampus.miniproject.config.auth.PrincipalDetails;
import com.fastcampus.miniproject.dto.ResponseWrapper;
import com.fastcampus.miniproject.dto.response.ProductResponse;
import com.fastcampus.miniproject.dto.response.ProductSimpleResponse;
import com.fastcampus.miniproject.service.ConcernProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConcernProductController {

    private final ConcernProductService concernProductService;

    @GetMapping("/products/concern")
    @ApiOperation(value = "관심상품 목록", notes = "사용자가 관심상품으로 등록한 상품 목록을 조회한다.")
    public ResponseWrapper<List<ProductResponse>> findAll(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return new ResponseWrapper<>(concernProductService.findAllById(principalDetails.getMember().getId()))
                .ok();
    }

    @PostMapping("/products/concern")
    @ApiOperation(value = "관심상품 등록", notes = "관심상품을 등록한다.")
    public ResponseWrapper<ProductSimpleResponse> register(@RequestBody Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        concernProductService.register(id, principalDetails.getMember().getId());

        return new ResponseWrapper<>(concernProductService.findById(id))
                .ok();
    }

    @DeleteMapping("/products/concern")
    @ApiOperation(value = "관심상품 삭제", notes = "관심상품을 삭제한다.")
    public ResponseWrapper<ProductSimpleResponse> delete(@RequestBody Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        concernProductService.delete(id, principalDetails.getMember().getId());

        return new ResponseWrapper<>(concernProductService.findById(id))
                .ok();
    }
}