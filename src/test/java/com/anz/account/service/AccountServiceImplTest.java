package com.anz.account.service;

import com.anz.account.dto.AccountType;
import com.anz.account.model.Account;
import com.anz.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    private static final String AUTH_TOKEN = "authToken";
    private static final String USER_ID = "userId";
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AuthService authService;

    private AccountServiceImpl accountService;
    private HttpHeaders httpHeaders;


    @BeforeEach
    public void setUp() {
        httpHeaders = new HttpHeaders();
        httpHeaders.put(HttpHeaders.AUTHORIZATION,List.of(AUTH_TOKEN));
        accountService = new AccountServiceImpl(authService,accountRepository, new ModelMapper());
    }

    @Test
    public void testViewAccounts() {

        when(authService.getUserIdByToken(eq(AUTH_TOKEN))).thenReturn(USER_ID);
        when(accountRepository.findAllByUserId(eq(USER_ID))).thenReturn(List.of(buildAccount()));
        List<com.anz.account.dto.Account> accounts = accountService.viewAccounts(httpHeaders);

        verify(accountRepository,times(1)).findAllByUserId(USER_ID);
        assertEquals(1,accounts.size());
        assertEquals("acctName",accounts.get(0).getAccountName());
        assertEquals("acctNumber",accounts.get(0).getAccountNumber());
        assertEquals(LocalDate.of(2018,10,10),accounts.get(0).getBalanceDate());
        assertEquals(AccountType.Savings,accounts.get(0).getAccountType());
        assertEquals("SGD",accounts.get(0).getCurrency());
        assertEquals(BigDecimal.valueOf(100.75),accounts.get(0).getOpeningAvailableBalance());
    }

    @Test
    public void testMultipleViewAccounts() {

        when(authService.getUserIdByToken(eq(AUTH_TOKEN))).thenReturn(USER_ID);
        when(accountRepository.findAllByUserId(eq(USER_ID))).thenReturn(List.of(buildAccount(),buildAccount(),buildAccount()));
        List<com.anz.account.dto.Account> accounts = accountService.viewAccounts(httpHeaders);

        verify(accountRepository,times(1)).findAllByUserId(USER_ID);
        assertEquals(3,accounts.size());
    }

    @Test
    public void testEmptyViewAccounts() {

        when(authService.getUserIdByToken(eq(AUTH_TOKEN))).thenReturn(USER_ID);
        when(accountRepository.findAllByUserId(eq(USER_ID))).thenReturn(List.of());
        List<com.anz.account.dto.Account> accounts = accountService.viewAccounts(httpHeaders);

        verify(accountRepository,times(1)).findAllByUserId(USER_ID);
        assertEquals(0,accounts.size());
    }

    @Test
    public void testViewAccountsWhenAccessTokenIsNullOrEmpty() {

        when(authService.getUserIdByToken(eq(AUTH_TOKEN))).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> accountService.viewAccounts(httpHeaders));
        verify(accountRepository,times(0)).findAllByUserId(anyString());

        when(authService.getUserIdByToken(eq(AUTH_TOKEN))).thenReturn("");
        assertThrows(IllegalArgumentException.class, () -> accountService.viewAccounts(httpHeaders));
        verify(accountRepository,times(0)).findAllByUserId(anyString());
    }

    private Account buildAccount() {
        return Account.builder()
                .id("1")
                .accountName("acctName")
                .accountNumber("acctNumber")
                .balanceDate(LocalDate.of(2018,10,10))
                .accountType(AccountType.Savings)
                .currency("SGD")
                .openingAvailableBalance(BigDecimal.valueOf(100.75))
                .build();
    }
}