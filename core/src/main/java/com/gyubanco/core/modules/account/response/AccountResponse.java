package com.gyubanco.core.modules.account.response;

import java.time.LocalDateTime;

import com.gyubanco.core.modules.account.model.Account;
import com.gyubanco.core.modules.account.model.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
  private Long id;
  private Long customerId;
  private String accountNumber;
  private Long balance;
  private LocalDateTime createdAt;
  private AccountStatus status;

  public static AccountResponse fromModel(Account account) {
    return AccountResponse.builder()
        .id(account.getId())
        .customerId(account.getCustomerId())
        .accountNumber(account.getAccountNumber())
        .balance(account.getBalance())
        .createdAt(account.getCreatedAt())
        .status(account.getStatus())
        .build();
  }
}
