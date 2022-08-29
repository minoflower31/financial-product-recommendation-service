package com.fastcampus.miniproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.OK;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T>{

    private T data;
    private int code;
    private String status;

    public ResponseWrapper(T data) {
        this.data = data;
    }

    public ResponseWrapper() {
    }

    public ResponseWrapper<T> ok() {
        this.code = OK.value();
        this.status = OK.getReasonPhrase();
        return this;
    }

    public ResponseWrapper<T> noContent() {
        this.code = NO_CONTENT.value();
        this.status = NO_CONTENT.getReasonPhrase();
        return this;
    }

}
