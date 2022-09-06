package com.fastcampus.miniproject.dto.request;

import com.fastcampus.miniproject.entity.ConcernProduct;
import lombok.Data;

import java.util.List;

public class ProductRequestDto {
    @Data
    public static class ProductSearch {
        private String query;
        private List<String> tag;
        private List<String> tagContent;

        public boolean isFieldsNull() {
            return query == null && tag == null && tagContent == null;
        }
    }

    @Data
    public static class ConcernProduct{
        private Long id;
    }

}
