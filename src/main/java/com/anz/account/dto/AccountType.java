package com.anz.account.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccountType {

    Savings("Savings"),
    Current("Current");

    private final String value;

    public String getValue() {
        return value;
    }
}
