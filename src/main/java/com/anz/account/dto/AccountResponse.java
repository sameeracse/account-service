package com.anz.account.dto;

import lombok.*;

import java.util.List;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private List<Account> accounts;
}
