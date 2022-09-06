package com.fastcampus.miniproject.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductSearchRequest {

    String query;
    List<String> tag;
    List<String> tagContent;

    public boolean isFieldsNull(){
        return query == null && tag == null && tagContent == null;
    }

}