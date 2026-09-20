package com.gyubanco.core.modules.transfer.request;

import java.util.UUID;

import com.gyubanco.core.modules.transfer.model.Transfer;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
  @NotNull(message = "고객 ID는 필수입니다.")
  private Long customerId;

  @NotNull(message = "입금 계좌번호는 필수입니다.")
  private String depositAccountNumber;

  @NotNull(message = "출금 계좌번호는 필수입니다.")
  private String withdrawalAccountNumber;

  @NotNull(message = "요청 ID는 필수입니다.")
  private UUID requestId;

  @NotNull(message = "금액은 필수입니다.")
  private Long amount;

  public static Transfer toModel(TransferRequest request, Long depositAccountId, Long withdrawalAccountId) {
    return Transfer.builder()
        .depositAccountId(depositAccountId)
        .withdrawalAccountId(withdrawalAccountId)
        .amount(request.getAmount())
        .requestId(request.getRequestId())
        .build();
  }
}
