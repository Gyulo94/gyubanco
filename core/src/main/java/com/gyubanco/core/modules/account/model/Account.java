package com.gyubanco.core.modules.account.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
  private Long id;
  private Long customerId;
  private String accountNumber;
  private AccountType accountType;
  private Long balance;
  private LocalDateTime createdAt;
  private AccountStatus status;
}
