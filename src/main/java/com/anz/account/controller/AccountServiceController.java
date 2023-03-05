package com.anz.account.controller;

import com.anz.account.common.RegexConstants;
import com.anz.account.dto.AccountTransactionResponse;
import com.anz.account.dto.AccountResponse;
import com.anz.account.service.AccountService;
import com.anz.account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "accounts")
    public ResponseEntity<AccountResponse> getUserAccounts(@RequestHeader final HttpHeaders httpHeaders) {

        AccountResponse accountResponse = AccountResponse.builder()
                .accounts(accountService.viewAccounts(httpHeaders)).build();

        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping(value = "account/{accountId}/transactions")
    public ResponseEntity<AccountTransactionResponse> getAccountTransactionsByAccountId(
            @RequestHeader final HttpHeaders httpHeaders,
            @PathVariable @Valid @Pattern(regexp = RegexConstants.ALPHA_NUMERIC) final String accountId,
            @RequestParam @Min(value = 0, message = "Page no should not be less than zero") final int pageNo,
            @RequestParam @Min(value = 1, message = "Page size should not be less than one") final int pageSize) {

        AccountTransactionResponse accountTransactionResponse = AccountTransactionResponse.builder()
                .accountTransactions(transactionService.viewAccountTransactions(httpHeaders,accountId, pageNo, pageSize)).build();

        return ResponseEntity.ok(accountTransactionResponse);
    }
}
