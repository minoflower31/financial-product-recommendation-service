package com.fastcampus.miniproject.common;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JoinException extends RuntimeException{

    public JoinException(String message) {
        super(message);
    }
}
