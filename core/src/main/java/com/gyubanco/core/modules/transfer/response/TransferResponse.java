package com.gyubanco.core.modules.transfer.response;

import java.time.LocalDateTime;
import com.gyubanco.core.modules.transfer.model.Transfer;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class TransferResponse {
  private Long id;
  private UUID requestId;
  private Long depositAccountId;
  private Long withdrawalAccountId;
  private Long amount;
  private LocalDateTime createdAt;

  public static TransferResponse fromModel(Transfer transfer) {
    return TransferResponse.builder()
        .id(transfer.getId())
        .requestId(transfer.getRequestId())
        .depositAccountId(transfer.getDepositAccountId())
        .withdrawalAccountId(transfer.getWithdrawalAccountId())
        .amount(transfer.getAmount())
        .createdAt(transfer.getCreatedAt())
        .build();
  }
}
