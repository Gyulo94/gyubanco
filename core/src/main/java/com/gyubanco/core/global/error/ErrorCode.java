package com.gyubanco.core.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorCodeInterface {
  OK(HttpStatus.OK.value(), "성공"),
  BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
  SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 에러가 발생했습니다."),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "인증이 필요합니다."),
  FORBIDDEN(HttpStatus.FORBIDDEN.value(), "접근이 거부되었습니다."),
  INVALID_INPUT(HttpStatus.BAD_REQUEST.value(), "입력값이 유효하지 않습니다."),
  NOT_FOUND(HttpStatus.NOT_FOUND.value(), "요청하신 리소스를 찾을 수 없습니다."),

  // 고객 관련
  DUPLICATE_EMAIL(HttpStatus.CONFLICT.value(), "이미 존재하는 이메일입니다."),
  CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 고객 정보를 찾을 수 없습니다."),

  // 계좌 관련
  ACCOUNT_NUMBER_LIMIT_EXCEEDED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "계좌번호 발급 범위를 초과했습니다."),
  UNSUPPORTED_ACCOUNT_TYPE(HttpStatus.BAD_REQUEST.value(), "지원되지 않는 계좌 유형입니다."),
  DEPOSIT_ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "입금 계좌를 찾을 수 없습니다."),
  WITHDRAWAL_ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "출금 계좌를 찾을 수 없습니다."),

  // 이체 관련
  TRANSFER_AMOUNT_MUST_BE_GREATER_THAN_ZERO(HttpStatus.BAD_REQUEST.value(), "이체 금액은 0보다 커야 합니다."),
  WITHDRAWAL_BALANCE_INSUFFICIENT(HttpStatus.BAD_REQUEST.value(), "출금 계좌의 잔액이 부족합니다."),
  WITHDRAWAL_ACCOUNT_STATUS_IS_NOT_ACTIVE(HttpStatus.BAD_REQUEST.value(), "출금 계좌가 활성화 상태가 아닙니다."),
  DEPOSIT_AND_WITHDRAWAL_ACCOUNT_SAME(HttpStatus.BAD_REQUEST.value(), "입금 계좌와 출금 계좌가 동일할 수 없습니다."),
  TRANSFER_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "이미 존재하는 이체 요청입니다."),
  CREATE_TRANSFER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "이체에 실패했습니다.");

  private final Integer httpStatusCode;
  private final String message;
}
