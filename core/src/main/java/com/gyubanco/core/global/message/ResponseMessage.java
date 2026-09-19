package com.gyubanco.core.global.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage implements ResponseMessageInterface {
  CREATE_CUSTOMER_SUCCESS("고객이 성공적으로 생성되었습니다."),
  CREATE_ACCOUNT_SUCCESS("계좌가 성공적으로 생성되었습니다."),
  EXECUTE_TRANSFER_SUCCESS("이체가 성공하였습니다.");

  private final String message;

}
