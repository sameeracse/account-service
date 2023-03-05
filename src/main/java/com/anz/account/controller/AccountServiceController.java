package com.anz.account.controller;

import com.anz.account.common.RegexConstants;
import com.anz.account.dto.AccountTransactionResponse;
import com.anz.account.dto.AccountResponse;
import com.anz.account.service.AccountService;
import com.anz.account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;


@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class AccountServiceController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping(value = "user/{userId}/accounts")
    public ResponseEntity<AccountResponse> getAccountsByUserId(
            @PathVariable @Pattern(regexp = RegexConstants.ALPHA_NUMERIC) final String userId) {

        AccountResponse accountResponse = AccountResponse.builder()
                .accounts(accountService.viewAccounts(userId)).build();

        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping(value = "account/{accountId}/transactions")
    public ResponseEntity<AccountTransactionResponse> getAccountTransactionsByAccountId(
            @PathVariable @Valid @Pattern(regexp = RegexConstants.ALPHA_NUMERIC) final String accountId,
            @RequestParam @Min(value = 0, message = "Page no should not be less than zero") final int pageNo,
            @RequestParam @Min(value = 1, message = "Page size should not be less than one") final int pageSize) {

        AccountTransactionResponse accountTransactionResponse = AccountTransactionResponse.builder()
                .accountTransactions(transactionService.viewAccountTransactions(accountId, pageNo, pageSize)).build();

        return ResponseEntity.ok(accountTransactionResponse);
    }
}
