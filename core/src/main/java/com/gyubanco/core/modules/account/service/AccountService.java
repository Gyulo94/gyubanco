package com.gyubanco.core.modules.account.service;

import org.springframework.stereotype.Service;

import com.gyubanco.core.global.error.ErrorCode;
import com.gyubanco.core.global.exception.ApiException;
import com.gyubanco.core.modules.account.generator.AccountNumberGenerator;
import com.gyubanco.core.modules.account.mapper.AccountMapper;
import com.gyubanco.core.modules.account.model.Account;
import com.gyubanco.core.modules.account.model.AccountType;
import com.gyubanco.core.modules.account.request.AccountCreateRequest;
import com.gyubanco.core.modules.account.response.AccountResponse;
import com.gyubanco.core.modules.customer.mapper.CustomerMapper;
import com.gyubanco.core.modules.customer.model.Customer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountMapper accountMapper;
  private final CustomerMapper customerMapper;
  private final AccountNumberGenerator accountNumberGenerator;

  public AccountResponse create(AccountCreateRequest request) {

    Customer customer = customerMapper.getById(request.getCustomerId());

    if (customer == null) {
      throw new ApiException(ErrorCode.CUSTOMER_NOT_FOUND);
    }

    AccountType accountType = request.getAccountType();

    if (accountType != AccountType.DEMAND_DEPOSIT) {
      throw new ApiException(ErrorCode.UNSUPPORTED_ACCOUNT_TYPE);
    }

    String accountNumber = accountNumberGenerator.generate(accountType);

    System.out.println("Generated account number: " + accountNumber);

    Account newAccount = AccountCreateRequest.toModel(request, accountNumber);

    newAccount = accountMapper.insertAccount(newAccount);
    AccountResponse response = AccountResponse.fromModel(newAccount);
    return response;
  }

}
