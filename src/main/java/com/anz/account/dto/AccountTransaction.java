package com.anz.account.dto;

import com.anz.account.common.RegexConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {

    @NotBlank
    private String accountId;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String accountName;

    private LocalDate valueDate;

    @NotBlank
    @Pattern(regexp = RegexConstants.CURRENCY_CODE)
    private String currency;

    @NotBlank
    private BigDecimal amount;

    @NotBlank
    private TransactionType transactionType;

    private String transactionNarrative;
}
