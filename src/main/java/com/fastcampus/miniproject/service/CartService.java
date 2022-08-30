package com.fastcampus.miniproject.service;

import com.fastcampus.miniproject.dto.response.ProductResponse;
import com.fastcampus.miniproject.entity.Cart;
import com.fastcampus.miniproject.entity.Member;
import com.fastcampus.miniproject.entity.Product;
import com.fastcampus.miniproject.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final MemberService memberService;

    @Transactional
    public void add(Long productId, Long memberId) {
        Member member = memberService.findById(memberId);
        Product product = productService.findById(productId);

        Cart cart = new Cart(member, product);
        cartRepository.save(cart);
        //memberId = 1, productId = 3; CartId = 1
        //memberId = 1, productId = 4; CartId = 2;
        //memberId = 1, productId = 5; CartId = 3;
        //memberId = 1, productId = 1; CartId = 4 ;
    }

    public List<ProductResponse> findMemberId(Long memberId){

        return cartRepository.findByMemberId(memberId)
                .stream()
                .map(c -> new ProductResponse(c.getProduct()))
                .toList();
    }

    @Transactional
    public void delete(Long productId, Long memberId){
        Cart target = cartRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new RuntimeException("해당하는 상품이 존재하지 않습니다!"));

        cartRepository.delete(target);
    }

}
