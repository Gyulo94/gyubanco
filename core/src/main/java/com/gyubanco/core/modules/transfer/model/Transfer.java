package com.gyubanco.core.modules.transfer.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Transfer {
  private Long id;
  private UUID requestId;
  private Long depositAccountId;
  private Long withdrawalAccountId;
  private Long amount;
  private LocalDateTime createdAt;
}
