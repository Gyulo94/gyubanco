package com.gyubanco.core.modules.customer.response;

import com.gyubanco.core.modules.customer.model.Customer;

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
public class CustomerResponse {
  private Long id;
  private String name;
  private String email;

  public static CustomerResponse fromModel(Customer customer) {
    return CustomerResponse.builder()
        .id(customer.getId())
        .name(customer.getName())
        .email(customer.getEmail())
        .build();
  }
}
