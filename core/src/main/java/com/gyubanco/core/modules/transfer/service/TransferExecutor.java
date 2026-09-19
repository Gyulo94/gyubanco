package com.gyubanco.core.modules.transfer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gyubanco.core.global.error.ErrorCode;
import com.gyubanco.core.global.exception.ApiException;
import com.gyubanco.core.modules.account.mapper.AccountMapper;
import com.gyubanco.core.modules.account.model.Account;
import com.gyubanco.core.modules.account.model.AccountStatus;
import com.gyubanco.core.modules.transfer.mapper.TransferMapper;
import com.gyubanco.core.modules.transfer.model.Transfer;
import com.gyubanco.core.modules.transfer.request.TransferRequest;
import com.gyubanco.core.modules.transfer.response.TransferResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferExecutor {

  private final TransferMapper transferMapper;
  private final AccountMapper accountMapper;

  @Transactional
  public TransferResponse execute(TransferRequest request) {

    // 서로 다른 계좌인지 검증
    validateDifferentAccounts(request);

    // 입금 계좌 조회
    Account depositAccount = getDepositAccount(
        request.getDepositAccountId());

    // 출금 계좌 조회
    Account withdrawalAccount = getWithdrawalAccount(
        request.getWithdrawalAccountId());

    // 출금 계좌 검증
    validateWithdrawalAccount(withdrawalAccount, request.getAmount());

    // 이체 기록 생성
    Transfer transfer = transferMapper.insertTransfer(
        TransferRequest.toModel(request));

    // 계좌 잔액 업데이트
    transferBalance(
        depositAccount.getId(),
        withdrawalAccount.getId(),
        request.getAmount());

    // 이체 완료 후 응답 반환
    TransferResponse response = TransferResponse.fromModel(transfer);
    return response;
  }

  // 서로 다른 계좌인지 검증하는 메서드
  private void validateDifferentAccounts(TransferRequest request) {

    // 입금 계좌와 출금 계좌가 동일한지 확인
    if (request.getDepositAccountId()
        .equals(request.getWithdrawalAccountId())) {

      // 동일한 계좌로의 이체는 허용되지 않음
      throw new ApiException(
          ErrorCode.DEPOSIT_AND_WITHDRAWAL_ACCOUNT_SAME);
    }
  }

  // 입금 계좌 조회 메서드
  private Account getDepositAccount(Long accountId) {

    // 입금 계좌 조회
    Account account = accountMapper.getById(accountId);

    // 입금 계좌가 존재하지 않으면 예외 발생
    if (account == null) {
      throw new ApiException(
          ErrorCode.DEPOSIT_ACCOUNT_NOT_FOUND);
    }

    // 입금 계좌가 활성 상태인지 확인
    return account;
  }

  // 출금 계좌 조회 메서드
  private Account getWithdrawalAccount(Long accountId) {

    // 출금 계좌 조회
    Account account = accountMapper.getById(accountId);

    // 출금 계좌가 존재하지 않으면 예외 발생
    if (account == null) {
      throw new ApiException(
          ErrorCode.WITHDRAWAL_ACCOUNT_NOT_FOUND);
    }

    // 출금 계좌가 활성 상태인지 확인
    return account;
  }

  // 출금 계좌 검증 메서드
  private void validateWithdrawalAccount(
      Account account,
      long amount) {

    // 출금 계좌가 활성 상태인지 확인
    if (account.getStatus() != AccountStatus.ACTIVE) {
      throw new ApiException(
          ErrorCode.WITHDRAWAL_ACCOUNT_STATUS_IS_NOT_ACTIVE);
    }

    // 출금 계좌의 잔액이 충분한지 확인
    if (account.getBalance() < amount) {
      throw new ApiException(
          ErrorCode.WITHDRAWAL_BALANCE_INSUFFICIENT);
    }
  }

  // 계좌 잔액 이체 메서드
  private void transferBalance(
      Long depositAccountId,
      Long withdrawalAccountId,
      long amount) {

    // 계좌 잔액 이체
    int updatedCount = accountMapper.transferBalance(
        depositAccountId,
        withdrawalAccountId,
        amount);

    // 이체가 정상적으로 이루어졌는지 확인
    if (updatedCount != 2) {
      throw new ApiException(
          ErrorCode.CREATE_TRANSFER_FAILED);
    }
  }
}