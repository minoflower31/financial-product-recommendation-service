package com.fastcampus.miniproject.dto.response;

import com.fastcampus.miniproject.dto.ProductCustomizedDto;
import com.fastcampus.miniproject.dto.ProductWisdomDto;
import com.fastcampus.miniproject.entity.Product;
import lombok.Data;

import java.util.List;

public class ProductResponseDto {
    @Data
    public static class ProductListCustom {
        private List<ProductCustomizedDto> loan;
        private List<ProductCustomizedDto> fund;
        private List<ProductCustomizedDto> saving;

        public ProductListCustom(List<ProductCustomizedDto> loan, List<ProductCustomizedDto> fund, List<ProductCustomizedDto> saving) {
            this.loan = loan;
            this.fund = fund;
            this.saving = saving;
        }
    }

    @Data
    public static class ProductListWisdom {
        private List<ProductWisdomDto> cards;
        private List<ProductWisdomDto> memberships;

        public ProductListWisdom(List<ProductWisdomDto> cards, List<ProductWisdomDto> memberships) {
            this.cards = cards;
            this.memberships = memberships;
        }
    }

    @Data
    public static class Product {
        private Long id;
        private String tag;
        private String description;
        private String logo;
        private String companyName;
        private String productName;
        private String price;

        public Product(com.fastcampus.miniproject.entity.Product product) {
            id = product.getId();
            tag = product.getTag();
            description = product.getDescription();
            logo = product.getLogo();
            companyName = product.getCompanyName();
            productName = product.getProductName();
            price = product.getPrice();
        }
    }

    @Data
    public static class ProductSimple {
        private Long id;
        private String productName;

        public ProductSimple(com.fastcampus.miniproject.entity.Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
        }
    }

}
