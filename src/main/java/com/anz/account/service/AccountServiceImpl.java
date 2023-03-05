package com.anz.account.service;

import com.anz.account.dto.Account;
import com.anz.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AuthService authService;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Account> viewAccounts(HttpHeaders httpHeaders) {

        String userId = authService.getUserIdByToken(httpHeaders.getFirst(HttpHeaders.AUTHORIZATION));

        if(userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("access token shouldn't be null or empty");
        }

        return accountRepository.findAllByUserId(userId).stream()
                .map(entity -> modelMapper.map(entity,Account.class))
                .collect(Collectors.toList());
    }
}
