package com.gyubanco.core.modules.customer.request;

import com.gyubanco.core.modules.customer.model.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
public class CustomerCreateRequest {

  @NotBlank(message = "이름은 필수입니다.")
  private String name;

  @NotBlank(message = "이메일은 필수입니다.")
  @Email(message = "올바른 이메일 형식이 아닙니다.")
  private String email;

  public static Customer toModel(CustomerCreateRequest request) {
    return Customer.builder()
        .name(request.getName())
        .email(request.getEmail())
        .build();
  }
}
