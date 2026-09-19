package com.gyubanco.core.modules.account.generator;

import org.springframework.stereotype.Component;

import com.gyubanco.core.global.error.ErrorCode;
import com.gyubanco.core.global.exception.ApiException;
import com.gyubanco.core.modules.account.mapper.AccountMapper;
import com.gyubanco.core.modules.account.model.AccountType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccountNumberGenerator {

  private final AccountMapper accountMapper;

  public String generate(AccountType accountType) {
    String prefix = accountType.getCode();

    long sequence = accountMapper.getNextAccountNumberSequence();

    String serial = "%010d".formatted(sequence);

    if (serial.length() != 10) {
      throw new ApiException(ErrorCode.ACCOUNT_NUMBER_LIMIT_EXCEEDED);
    }

    String baseNumber = prefix + serial;

    int checkDigit = calculateCheckDigit(baseNumber);

    return baseNumber + checkDigit;
  }

  private int calculateCheckDigit(String baseNumber) {
    int sum = 0;
    for (int i = 0; i < baseNumber.length(); i++) {
      int digit = Character.getNumericValue(baseNumber.charAt(i));
      sum += (i % 2 == 0) ? digit * 3 : digit * 1;
    }
    return (10 - (sum % 10)) % 10;
  }
}