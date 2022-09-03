package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.dto.request.ProductSearchRequest;
import com.fastcampus.miniproject.entity.AdditionalInfo;
import com.fastcampus.miniproject.entity.Product;
import com.fastcampus.miniproject.enums.Tag;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Supplier;

import static com.fastcampus.miniproject.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory qf;

    /**
     * 검색 조건이 없을 경우, 성능 최적화를 위해 table search 쿼리를 제대로 짜야함.
     */
    @Override
    public List<Product> findBySearchCond(ProductSearchRequest condition) {

        return qf.selectFrom(product)
                .where(
                        tagEq(condition.getTag())
                                .or(contentEq(condition.getTagContent()))
                                .or(nameEq(condition.getQuery()))
                )
                .fetch();
    }

    @Override
    public List<Product> findCustomizedList(Tag tag, AdditionalInfo additionalInfo) {

        return qf.selectFrom(product)
                .where(
                        product.tag.eq(tag.getValue()),
                        additionalEq(product.additionalInfo.age, additionalInfo.getAge())
                                .or(listEq(product.additionalInfo.interest, List.of(additionalInfo.getInterest().split("\\|"))))
                                .or(additionalEq(product.additionalInfo.job, additionalInfo.getJob()))
                                .or(additionalEq(product.additionalInfo.car, additionalInfo.getCar()))
                                .or(additionalEq(product.additionalInfo.asset, additionalInfo.getAsset()))
                                .or(additionalEq(product.additionalInfo.salary, additionalInfo.getSalary()))
                                .or(additionalEq(product.additionalInfo.realEstate, additionalInfo.getRealEstate()))
                )
                .fetch();
    }

    @Override
    public List<Product> findWisdomList(Tag tag, AdditionalInfo additionalInfo) {

        return qf.selectFrom(product)
                .where(
                        product.tag.eq(tag.getValue()),
                        listEq(product.additionalInfo.interest, List.of(additionalInfo.getInterest().split("\\|")))
                )
                .fetch();
    }

    private BooleanBuilder additionalEq(StringPath stringPath, String info) {

        return safeForNullValues(() -> {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            if (StringUtils.hasText(info)) {
                booleanBuilder.or(stringPath.contains(info));
                return booleanBuilder;
            }
            return booleanBuilder;
        });
    }

    private BooleanBuilder listEq(StringPath stringPath, List<String> interestList){

        return safeForNullValues(() -> {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            if(!interestList.isEmpty()) {
                interestList.forEach(e -> booleanBuilder.or(stringPath.contains(e)));
            }
            return booleanBuilder;
        });
    }

    private BooleanBuilder safeForNullValues(Supplier<BooleanBuilder> target) {

        try {
            return new BooleanBuilder(target.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }

    public BooleanBuilder contentEq(List<String> contentList) {

        return safeForNullValues(() -> {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            if (contentList != null && !contentList.isEmpty()) {
                contentList.forEach(e -> booleanBuilder.or(product.tagContent.contains(e)));
            }
            return booleanBuilder;
        });
    }

    public BooleanBuilder tagEq(List<String> list) {

        return safeForNullValues(() -> {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            if (list != null && !list.isEmpty()) {
                list.forEach(e -> booleanBuilder.or(product.tag.eq(e)));
            }
            return booleanBuilder;
        });
    }


    private BooleanBuilder nameEq(String query) {

        return safeForNullValues(() -> {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            if(StringUtils.hasText(query)) {
                booleanBuilder.or(product.productName.contains(query));
            }
            return booleanBuilder;
        });
    }


}
