package com.fastcampus.miniproject.dto.response;

import com.fastcampus.miniproject.dto.ProductCustomizedDto;
import com.fastcampus.miniproject.dto.TokenDto;
import com.fastcampus.miniproject.entity.AdditionalInfo;
import com.fastcampus.miniproject.entity.Member;
import com.fastcampus.miniproject.entity.Product;
import lombok.Data;

import java.util.List;

public class MemberResponseDto {

    @Data
    public static class GetMember {
        private String name;
        private String job;
        private List<String> interest;
        private String realEstate;
        private String car;
        private String asset;
        private String salary;
        private String age;

        public GetMember(String name, AdditionalInfo info) {
            this.name = name;
            job = info.getJob();
            interest = List.of(info.getInterest().split("\\|"));
            realEstate = info.getRealEstate();
            car = info.getCar();
            asset = info.getAsset();
            salary = info.getSalary();
            age = info.getAge();
        }
    }

    @Data
    public static class MemberAndToken {
        private String email;
        private String name;
        private String phoneNumber;
        private Boolean isNotFirst;
        private String accessToken;
        private String refreshToken;

        public MemberAndToken(Member member, TokenDto tokenDto) {
            this.email = member.getLoginId();
            this.name = member.getName();
            this.phoneNumber = member.getPhoneNumber();
            this.isNotFirst = member.getIsNotFirst();
            this.accessToken = tokenDto.getAccessToken();
            this.refreshToken = tokenDto.getRefreshToken();
        }
    }

    @Data
    public static class ProductDetail {
        private Long id;
        private String companyName;
        private String productName;
        private String description;
        private String tag;
        private List<String> tagContent;
        private List<String> details;
        private String logo;
        private String price;
        private List<String> age;
        private List<String> job;

        public ProductDetail(Product product) {
            id = product.getId();
            companyName = product.getCompanyName();
            productName = product.getProductName();
            description = product.getDescription();
            tag = product.getTag();
            tagContent = List.of(product.getTagContent().split("\\|"));
            details = List.of(product.getDetails().split("\\|"));
            logo = product.getLogo();
            price = product.getPrice();
            age = List.of(product.getAdditionalInfo().getAge().split("\\|"));
            job = List.of(product.getAdditionalInfo().getJob().split("\\|"));
        }
    }
}
