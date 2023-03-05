package com.anz.account.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransactionType {

    DEBIT("Debit"),
    CREDIT("Credit");

    private final String value;
}
