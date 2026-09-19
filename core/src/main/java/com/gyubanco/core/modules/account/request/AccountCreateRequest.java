package com.gyubanco.core.modules.account.request;

import com.gyubanco.core.modules.account.model.Account;
import com.gyubanco.core.modules.account.model.AccountType;

import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreateRequest {

  @NotNull(message = "이메일은 필수입니다.")
  private Long customerId;

  @NotNull(message = "계좌 유형은 필수입니다.")
  private AccountType accountType;

  public static Account toModel(AccountCreateRequest request, String accountNumber) {
    return Account.builder()
        .customerId(request.getCustomerId())
        .accountNumber(accountNumber)
        .accountType(request.getAccountType())
        .build();
  }
}
