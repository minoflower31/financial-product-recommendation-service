package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.dto.request.ProductSearchRequest;
import com.fastcampus.miniproject.entity.Product;
import com.fastcampus.miniproject.entity.QProduct;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.fastcampus.miniproject.entity.QProduct.*;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory qf;

    @Override
    public List<Product> findBySearchCond(ProductSearchRequest productSearchRequest) {

        List<String> tagList = productSearchRequest.getTag();
        String query = productSearchRequest.getQuery();

        if (productSearchRequest.isFieldsNull()) {
            return qf.selectFrom(product)
                    .fetch();
        }

        JPAQuery<Product> targetQuery = qf.selectFrom(product).where(
                productTagsIn(tagList),
                productNameContains(query)
        );

        List<String> contentList = productSearchRequest.getTagContent();

        if (contentList != null) {
            for (String content : contentList) {
                targetQuery.where(
                        productTagContentContains(content)
                );
            }
        }

        return targetQuery.fetch();
    }


    private Predicate productTagContentContains(String content) {
        return StringUtils.hasText(content) ? product.tagContent.contains(content) : null;
    }

    private Predicate productNameContains(String query) {
        return StringUtils.hasText(query) ? product.productName.contains(query.trim()) : null;
    }

    private Predicate productTagsIn(List<String> tagList) {
        return tagList != null ? product.tag.in(tagList) : null;
    }


}
