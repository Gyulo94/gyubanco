package com.gyubanco.core.modules.transfer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gyubanco.core.global.api.Api;
import com.gyubanco.core.global.message.ResponseMessage;
import com.gyubanco.core.modules.transfer.request.TransferRequest;
import com.gyubanco.core.modules.transfer.response.TransferResponse;
import com.gyubanco.core.modules.transfer.service.TransferService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("transfers")
@RequiredArgsConstructor
public class TransferController {
  private final TransferService transferService;

  /**
   * 이체 요청을 처리하는 엔드포인트
   * 
   * @param TransferRequest
   * @return TransferResponse
   */
  @PostMapping
  public Api<TransferResponse> executeTransfer(@Valid @RequestBody TransferRequest request) {
    TransferResponse response = transferService.executeTransfer(request);
    return Api.OK(response, ResponseMessage.EXECUTE_TRANSFER_SUCCESS);
  }
}
