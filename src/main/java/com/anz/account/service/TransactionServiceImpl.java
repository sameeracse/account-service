package com.anz.account.service;

import com.anz.account.dto.AccountTransaction;
import com.anz.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final AuthService authService;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AccountTransaction> viewAccountTransactions(final HttpHeaders httpHeaders , final String accountId, final int pageNo, final int pageSize) {

        String userId = authService.getUserIdByToken(httpHeaders.getFirst(HttpHeaders.AUTHORIZATION));

        if(userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("Access Token shouldn't be null or empty");
        }

        if(accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("Account ID shouldn't be null or empty");
        }

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("transactionTime"));

        return transactionRepository.findByAccountIdAndAccountUserId(userId,accountId, paging).stream()
                .map(entity -> modelMapper.map(entity, AccountTransaction.class)).
                collect(Collectors.toList());
    }
}
