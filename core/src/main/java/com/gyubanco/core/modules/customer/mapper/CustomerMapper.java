package com.gyubanco.core.modules.customer.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gyubanco.core.modules.customer.model.Customer;

@Mapper
public interface CustomerMapper {

  void insertCustomer(Customer customer);

  boolean existsByEmail(String email);

}
