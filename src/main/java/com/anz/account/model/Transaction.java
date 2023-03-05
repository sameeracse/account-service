package com.anz.account.model;

import com.anz.account.dto.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;

    @Column(name="value_date")
    private LocalDate valueDate;

    @Column(name="currency", length=10, nullable=false)
    private String currency;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name="transaction_narrative")
    private String transactionNarrative;

    @Column(name="transaction_time")
    private LocalDateTime transactionTime;
}
