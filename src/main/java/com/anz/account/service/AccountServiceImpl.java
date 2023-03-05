package com.anz.account.service;

import com.anz.account.dto.Account;
import com.anz.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Account> viewAccounts(String userId) {

        if(userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("user ID shouldn't be null or empty");
        }

        return accountRepository.findAllByUserId(userId).stream()
                .map(entity -> modelMapper.map(entity,Account.class))
                .collect(Collectors.toList());
    }
}
