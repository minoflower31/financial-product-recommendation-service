package com.fastcampus.miniproject.dto.response;

import com.fastcampus.miniproject.dto.ProductWisdomDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductListWisdomResponse {

    private List<ProductWisdomDto> cards;
    private List<ProductWisdomDto> memberships;

    public ProductListWisdomResponse(List<ProductWisdomDto> cards, List<ProductWisdomDto> memberships) {
        this.cards = cards;
        this.memberships = memberships;
    }
}