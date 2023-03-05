package com.anz.account.repository;

import com.anz.account.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction,Long> {

    Page<Transaction> findByAccountIdAndAccountUserId(String userId, String accountId, Pageable pageable);
}
