package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.entity.AdditionalInfo;
import com.fastcampus.miniproject.entity.Product;
import com.fastcampus.miniproject.enums.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductRepositoryImplTest {

    @Autowired
    ProductRepositoryImpl productRepository;
    @Autowired
    TestEntityManager tem;
    JPAQueryFactory qf;

    @BeforeEach
    void init() {
        qf = new JPAQueryFactory(tem.getEntityManager());
    }

    @ParameterizedTest
    @MethodSource("fixture4dynamicQuery")
    void parameterized(final String interest, final Tag tag, final int assertSize) {

        AdditionalInfo additionalInfo = new AdditionalInfo("", interest, "", "", "", "", "");
        List<ProductWisdomDtoTemp> list = productRepository.findWisdomList(tag, additionalInfo)
                .stream()
                .map(ProductWisdomDtoTemp::new)
                .toList();

        assertThat(list.size()).isSameAs(assertSize);


        for (ProductWisdomDtoTemp productWisdomDtoTemp : list) {
            assertThat(productWisdomDtoTemp.getTag()).isEqualTo("멤버십");
        }
    }

    private static Stream<Arguments> fixture4dynamicQuery() {
        return Stream.of(
                Arguments.of("코딩", Tag.MEMBERSHIP, 2),
                Arguments.of("코딩|재테크", Tag.MEMBERSHIP, 3),
                Arguments.of("코딩|재테크|영화", Tag.MEMBERSHIP, 5)
        );
    }

    @Data
    static class ProductWisdomDtoTemp {

        private final String tag;
        private final List<String> interestList;

        public ProductWisdomDtoTemp(Product product) {
            this.tag = product.getTag();
            this.interestList = List.of(product.getAdditionalInfo().getInterest().split("\\|"));
        }


    }

}