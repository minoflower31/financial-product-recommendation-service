package com.fastcampus.miniproject.enums;

public enum Tag {
    LOAN("대출"),
    FUND("펀드"),
    CARD("카드"),
    SAVING("적금"),
    MEMBERSHIP("멤버십");

    private final String value;

    Tag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
