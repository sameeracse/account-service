package com.anz.account.dto;

import com.anz.account.common.RegexConstants;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @NotBlank
    private String id;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String accountName;

    @NotBlank
    private AccountType accountType;

    private LocalDate balanceDate;

    @NotBlank
    @Pattern(regexp = RegexConstants.CURRENCY_CODE)
    private String currency;

    private BigDecimal openingAvailableBalance;

    @NotBlank
    private String userId;
}
