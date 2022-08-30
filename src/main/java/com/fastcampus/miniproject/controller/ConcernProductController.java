package com.fastcampus.miniproject.controller;

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
    public ResponseWrapper<List<ProductResponse>> findAll(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return new ResponseWrapper<>(concernProductService.findAllById(principalDetails.getId()))
                .ok();
    }

    @PostMapping("/products/concern")
    public ResponseWrapper<ProductSimpleResponse> register(@RequestBody Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        concernProductService.register(id, principalDetails.getId());

        return new ResponseWrapper<>(concernProductService.findById(id))
            .ok();
}

    @DeleteMapping("/products/concern")
    public ResponseWrapper<ProductSimpleResponse> delete(@RequestBody Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        concernProductService.delete(id, principalDetails.getId());

        return new ResponseWrapper<>(concernProductService.findById(id))
                .ok();
    }

}
