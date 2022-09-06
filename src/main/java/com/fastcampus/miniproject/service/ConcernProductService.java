package com.fastcampus.miniproject.service;

import com.fastcampus.miniproject.dto.response.ProductResponseDto;
import com.fastcampus.miniproject.entity.ConcernProduct;
import com.fastcampus.miniproject.entity.Member;
import com.fastcampus.miniproject.entity.Product;
import com.fastcampus.miniproject.repository.ConcernProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcernProductService {

    private final ConcernProductRepository concernProductRepository;
    private final MemberService memberService;
    private final ProductService productService;
    public List<ProductResponseDto.Product> findAllById(Long memberId) {
        return concernProductRepository.findByMemberId(memberId)
                .stream()
                .map(c -> new ProductResponseDto.Product(c.getProduct()))
                .toList();
    }

    @Transactional
    public void register(Long productId, Long memberId) {
        System.out.println("memberId = " + memberId);
        Member member = memberService.findById(memberId);
        Product product = productService.findById(productId);

        ConcernProduct concernProduct = new ConcernProduct(member, product);
        concernProductRepository.save(concernProduct);
    }

    public ProductResponseDto.ProductSimple findById(Long id) {
        return new ProductResponseDto.ProductSimple(productService.findById(id));
    }

    @Transactional
    public void delete(Long productId, Long memberId) {
        ConcernProduct concernProduct = concernProductRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));

        concernProductRepository.delete(concernProduct);
    }

}
