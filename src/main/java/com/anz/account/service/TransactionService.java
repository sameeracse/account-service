package com.anz.account.service;

import com.anz.account.dto.AccountTransaction;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface TransactionService {

    List<AccountTransaction> viewAccountTransactions(HttpHeaders httpHeaders, String accountId, int pageNo, int pageSize);
}
