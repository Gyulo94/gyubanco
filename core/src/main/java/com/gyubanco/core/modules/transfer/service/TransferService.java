package com.gyubanco.core.modules.transfer.service;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gyubanco.core.global.error.ErrorCode;
import com.gyubanco.core.global.exception.ApiException;
import com.gyubanco.core.modules.account.mapper.AccountMapper;
import com.gyubanco.core.modules.account.model.Account;
import com.gyubanco.core.modules.customer.mapper.CustomerMapper;
import com.gyubanco.core.modules.customer.model.Customer;
import com.gyubanco.core.modules.transfer.mapper.TransferMapper;
import com.gyubanco.core.modules.transfer.model.Transfer;
import com.gyubanco.core.modules.transfer.request.TransferRequest;
import com.gyubanco.core.modules.transfer.response.TransferResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {
  private final TransferMapper transferMapper;
  private final TransferExecutor transferExecutor;
  private final AccountMapper accountMapper;
  private final CustomerMapper customerMapper;

  private static final String UNIQUE_VIOLATION = "23505";
  private static final String REQUEST_ID_CONSTRAINT = "transfer_request_id_key";

  public TransferResponse executeTransfer(TransferRequest request) {

    TransferResponse existing = checkExistingTransfer(request);

    // 이미 존재하는 이체 요청인지 확인
    if (existing != null) {
      return existing;
    }

    // 요청 검증 전에 기존 이체 요청이 있는지 확인
    validateRequest(request);

    try {
      // 이체 실행
      return transferExecutor.execute(request);
    } catch (DataIntegrityViolationException e) {

      // 중복된 이체 요청 처리
      return recoverDuplicateTransfer(request, e);
    }
  }

  // 요청 검증 메서드
  private void validateRequest(TransferRequest request) {
    // 요청된 고객 ID로 고객 조회
    Customer customer = customerMapper.getById(request.getCustomerId());

    // 고객이 존재하지 않으면 예외 발생
    if (customer == null) {
      throw new ApiException(ErrorCode.CUSTOMER_NOT_FOUND);
    }

    // 이체 금액이 0 이하인지 확인
    if (request.getAmount() <= 0) {
      throw new ApiException(ErrorCode.TRANSFER_AMOUNT_MUST_BE_GREATER_THAN_ZERO);
    }
  }

  // 중복된 이체 요청을 복구하는 메서드
  private TransferResponse recoverDuplicateTransfer(TransferRequest request,
      DataIntegrityViolationException exception) {

    // 요청 ID 충돌이 아닌 경우 예외를 다시 던짐
    if (!isRequestIdConflict(exception)) {
      throw exception;
    }

    log.debug("동시 중복 요청 감지 requestId={}", request.getRequestId());

    // 중복된 이체 요청이 이미 존재하는지 확인
    TransferResponse existing = checkExistingTransfer(request);

    // 기존 이체가 존재하면 해당 이체를 반환하고, 존재하지 않으면 null 반환
    if (existing == null) {
      return existing;
    }
    // 기존 이체가 존재하지 않으면 null 반환
    return existing;
  }

  // 요청 ID 충돌 여부를 확인하는 메서드
  private boolean isRequestIdConflict(DataIntegrityViolationException exception) {
    Throwable cause = exception;
    while (cause != null) {
      // PSQLException인지 확인하고, 서버 오류 메시지를 확인하여 요청 ID 충돌인지 판단
      if (cause instanceof PSQLException psqlException) {

        // 서버 오류 메시지 가져오기
        var serverError = psqlException.getServerErrorMessage();

        // 요청 ID 충돌인지 확인
        return UNIQUE_VIOLATION.equals(serverError.getSQLState()) && serverError != null
            && REQUEST_ID_CONSTRAINT.equals(serverError.getConstraint());
      }
      // 다음 원인으로 이동
      cause = cause.getCause();
    }
    // 모든 원인을 확인했지만 요청 ID 충돌이 아닌 경우 false 반환
    return false;
  }

  // 이미 존재하는 이체 요청을 확인하는 메서드
  public TransferResponse checkExistingTransfer(TransferRequest request) {

    // 기존 이체 요청 조회
    Transfer existingTransfer = transferMapper.getByRequestId(request.getRequestId());

    // 기존 이체 요청이 존재하지 않으면 null 반환
    if (existingTransfer == null) {
      return null;
    }

    // 입금 계좌와 출금 계좌 조회
    Account depositAccount = accountMapper.getByAccountNumber(
        request.getDepositAccountNumber());

    // 출금 계좌 조회
    Account withdrawalAccount = accountMapper.getByAccountNumber(
        request.getWithdrawalAccountNumber());

    // 기존 이체 요청과 현재 요청이 동일한지 확인
    boolean sameRequest = depositAccount != null
        && withdrawalAccount != null
        && existingTransfer.getDepositAccountId()
            .equals(depositAccount.getId())
        && existingTransfer.getWithdrawalAccountId()
            .equals(withdrawalAccount.getId())
        && existingTransfer.getAmount()
            .equals(request.getAmount());

    // 동일한 요청이 아닌 경우 예외 발생
    if (!sameRequest) {
      throw new ApiException(ErrorCode.TRANSFER_ALREADY_EXISTS);
    }

    // 동일한 요청인 경우 기존 이체 정보를 반환
    TransferResponse response = TransferResponse.fromModel(existingTransfer);
    return response;
  }
}
