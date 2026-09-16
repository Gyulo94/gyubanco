package com.gyubanco.core.modules.customer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gyubanco.core.global.api.Api;
import com.gyubanco.core.global.message.ResponseMessage;
import com.gyubanco.core.modules.customer.request.CustomerCreateRequest;
import com.gyubanco.core.modules.customer.response.CustomerResponse;
import com.gyubanco.core.modules.customer.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping
  public Api<CustomerResponse> createCustomer(@Valid @RequestBody CustomerCreateRequest request) {
    CustomerResponse response = customerService.create(request);
    return Api.OK(response, ResponseMessage.CREATE_CUSTOMER_SUCCESS);
  }

}
