package com.gyubanco.core.modules.customer.service;

import org.springframework.stereotype.Service;

import com.gyubanco.core.global.error.ErrorCode;
import com.gyubanco.core.global.exception.ApiException;
import com.gyubanco.core.modules.customer.mapper.CustomerMapper;
import com.gyubanco.core.modules.customer.model.Customer;
import com.gyubanco.core.modules.customer.request.CustomerCreateRequest;
import com.gyubanco.core.modules.customer.response.CustomerResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerMapper customerMapper;

  public CustomerResponse create(CustomerCreateRequest request) {
    Customer newCustomer = CustomerCreateRequest.toModel(request);

    if (customerMapper.existsByEmail(request.getEmail())) {
      throw new ApiException(ErrorCode.DUPLICATE_EMAIL);
    }

    customerMapper.insertCustomer(newCustomer);
    CustomerResponse response = CustomerResponse.fromModel(newCustomer);
    return response;
  }

}
