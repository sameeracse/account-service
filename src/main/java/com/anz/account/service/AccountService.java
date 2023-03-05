package com.anz.account.service;

import com.anz.account.dto.Account;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface AccountService {

    List<Account> viewAccounts( HttpHeaders httpHeaders);
}
