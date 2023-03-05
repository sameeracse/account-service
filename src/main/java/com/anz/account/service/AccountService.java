package com.anz.account.service;

import com.anz.account.dto.Account;

import java.util.List;

public interface AccountService {

    List<Account> viewAccounts(String userId);
}
