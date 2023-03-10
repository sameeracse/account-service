package com.anz.account.service;

import com.anz.account.dto.AccountTransaction;
import com.anz.account.dto.AccountType;
import com.anz.account.dto.TransactionType;
import com.anz.account.model.Account;
import com.anz.account.model.Transaction;
import com.anz.account.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    private static final String AUTH_TOKEN = "authToken";
    private static final String USER_ID = "userId";

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AuthService authService;

    private TransactionServiceImpl transactionService;

    private HttpHeaders httpHeaders;

    @BeforeEach
    public void setUp() {
        httpHeaders = new HttpHeaders();
        httpHeaders.put(HttpHeaders.AUTHORIZATION,List.of(AUTH_TOKEN));
        transactionService = new TransactionServiceImpl(authService, transactionRepository, new ModelMapper());
    }

    @Test
    public void testViewAccountTransactions() {

        String accountId = "accountId";
        int pageSize = 10;
        int pageNo = 1;

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("transactionTime"));
        Page<Transaction> pageResult = new PageImpl<>(List.of(buildTransaction()),paging,10);

        when(authService.getUserIdByToken(eq(AUTH_TOKEN))).thenReturn(USER_ID);
        when(transactionRepository.findByAccountIdAndAccountUserId(eq(USER_ID),eq(accountId), any())).thenReturn(pageResult);
        List<AccountTransaction> accountTransactions= transactionService.viewAccountTransactions(httpHeaders,accountId,pageNo,pageSize);

        verify(transactionRepository,times(1)).findByAccountIdAndAccountUserId(eq(USER_ID),eq(accountId),eq(paging));
        assertEquals(1,accountTransactions.size());
        assertEquals("1",accountTransactions.get(0).getAccountId());
        assertEquals("acctNumber",accountTransactions.get(0).getAccountNumber());
        assertEquals("acctName",accountTransactions.get(0).getAccountName());
        assertEquals(LocalDate.of(2019,01,10),accountTransactions.get(0).getValueDate());
        assertEquals("AUD",accountTransactions.get(0).getCurrency());
        assertEquals(BigDecimal.valueOf(277.12),accountTransactions.get(0).getAmount());
        assertEquals(TransactionType.DEBIT,accountTransactions.get(0).getTransactionType());
        assertEquals("description",accountTransactions.get(0).getTransactionNarrative());

    }

    @Test
    public void testMultipleAccountTransactions() {

        String accountId = "accountId";
        int pageSize = 10;
        int pageNo = 1;

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("transactionTime"));
        Page<Transaction> pageResult = new PageImpl<>(List.of(buildTransaction(),buildTransaction()),paging,10);

        when(authService.getUserIdByToken(eq(AUTH_TOKEN))).thenReturn(USER_ID);
        when(transactionRepository.findByAccountIdAndAccountUserId(eq(USER_ID),eq(accountId),any())).thenReturn(pageResult);
        List<AccountTransaction> accountTransactions= transactionService.viewAccountTransactions(httpHeaders,accountId,pageNo,pageSize);

        verify(transactionRepository,times(1)).findByAccountIdAndAccountUserId(eq(USER_ID),eq(accountId),eq(paging));
        assertEquals(2,accountTransactions.size());
    }

    @Test
    public void testEmptyAccountTransactions() {

        String accountId = "accountId";
        int pageSize = 10;
        int pageNo = 1;

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("transactionTime"));
        Page<Transaction> pageResult = new PageImpl<>(List.of(),paging,10);

        when(authService.getUserIdByToken(eq(AUTH_TOKEN))).thenReturn(USER_ID);
        when(transactionRepository.findByAccountIdAndAccountUserId(eq(USER_ID),eq(accountId), any())).thenReturn(pageResult);
        List<AccountTransaction> accountTransactions= transactionService.viewAccountTransactions(httpHeaders,accountId,pageNo,pageSize);

        verify(transactionRepository,times(1)).findByAccountIdAndAccountUserId(USER_ID,accountId,paging);
        assertEquals(0,accountTransactions.size());
    }

    @Test
    public void testAccountTransactionsWhenAccountIdIsNullOrEmpty() {
        int pageSize = 10;
        int pageNo = 1;

        assertThrows(IllegalArgumentException.class, () -> transactionService.viewAccountTransactions(httpHeaders,null,pageSize,pageNo));
        verify(transactionRepository,times(0)).findByAccountIdAndAccountUserId(anyString(),anyString(),any());

        assertThrows(IllegalArgumentException.class, () -> transactionService.viewAccountTransactions(httpHeaders,"",pageSize,pageNo));
        verify(transactionRepository,times(0)).findByAccountIdAndAccountUserId(anyString(),anyString(),any());
    }

    private Transaction buildTransaction() {
        return Transaction.builder()
                .id("1")
                .account(buildAccount())
                .valueDate(LocalDate.of(2019,01,10))
                .currency("AUD")
                .amount(BigDecimal.valueOf(277.12))
                .transactionType(TransactionType.DEBIT)
                .transactionNarrative("description")
                .build();
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