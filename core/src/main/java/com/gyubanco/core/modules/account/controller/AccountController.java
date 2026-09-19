package com.gyubanco.core.modules.account.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gyubanco.core.global.api.Api;
import com.gyubanco.core.global.message.ResponseMessage;
import com.gyubanco.core.modules.account.request.AccountCreateRequest;
import com.gyubanco.core.modules.account.response.AccountResponse;
import com.gyubanco.core.modules.account.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @PostMapping
  public Api<AccountResponse> createAccount(@Valid @RequestBody AccountCreateRequest request) {
    AccountResponse response = accountService.create(request);
    return Api.OK(response, ResponseMessage.CREATE_ACCOUNT_SUCCESS);
  }

}
