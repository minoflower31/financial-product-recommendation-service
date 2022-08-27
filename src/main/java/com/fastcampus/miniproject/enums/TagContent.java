package com.fastcampus.miniproject.enums;

public enum TagContent {

    YOUTH("청년"),
    COVID("코로나");

    private final String value;

    TagContent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
