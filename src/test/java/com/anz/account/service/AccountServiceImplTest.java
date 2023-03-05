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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUp(){
        accountService = new AccountServiceImpl(accountRepository, new ModelMapper());
    }

    @Test
    public void testViewAccounts() {

        String userId = "userId";

        when(accountRepository.findAllByUserId(eq(userId))).thenReturn(List.of(buildAccount()));
        List<com.anz.account.dto.Account> accounts = accountService.viewAccounts(userId);

        verify(accountRepository,times(1)).findAllByUserId(userId);
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

        String userId = "userId";

        when(accountRepository.findAllByUserId(eq(userId))).thenReturn(List.of(buildAccount(),buildAccount(),buildAccount()));
        List<com.anz.account.dto.Account> accounts = accountService.viewAccounts(userId);

        verify(accountRepository,times(1)).findAllByUserId(userId);
        assertEquals(3,accounts.size());
    }

    @Test
    public void testEmptyViewAccounts() {

        String userId = "userId";

        when(accountRepository.findAllByUserId(eq(userId))).thenReturn(List.of());
        List<com.anz.account.dto.Account> accounts = accountService.viewAccounts(userId);

        verify(accountRepository,times(1)).findAllByUserId(userId);
        assertEquals(0,accounts.size());
    }

    @Test
    public void testViewAccountsWhenUserIdIsNullOrEmpty() {

        assertThrows(IllegalArgumentException.class, () -> accountService.viewAccounts(null));
        verify(accountRepository,times(0)).findAllByUserId(anyString());

        assertThrows(IllegalArgumentException.class, () -> accountService.viewAccounts(""));
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