package com.anz.account.service;

import com.anz.account.dto.AccountTransaction;

import java.util.List;

public interface TransactionService {

    List<AccountTransaction> viewAccountTransactions(String accountId, int pageNo, int pageSize);
}
