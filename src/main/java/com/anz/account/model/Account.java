package com.anz.account.model;

import com.anz.account.dto.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name="account_number", length=50, nullable=false, unique=true)
    private String accountNumber;

    @Column(name="account_name", length=50, nullable=false)
    private String accountName;

    @Column(name="account_type", length=20, nullable=false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name="balance_date", length=10, nullable=false)
    private LocalDate balanceDate;

    @Column(name="currency", length=10, nullable=false)
    private String currency;

    @Column(name="opening_available_balance", length=10)
    private BigDecimal openingAvailableBalance;

    @Column(name="user_id", nullable = false)
    private String userId;
}
