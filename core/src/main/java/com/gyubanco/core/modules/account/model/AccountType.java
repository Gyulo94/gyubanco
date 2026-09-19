package com.gyubanco.core.modules.account.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {
  DEMAND_DEPOSIT("100"),
  TIME_DEPOSIT("200"),
  INSTALLMENT_SAVINGS("300");

  private final String code;
}
