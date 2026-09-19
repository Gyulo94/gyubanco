package com.gyubanco.core.modules.transfer.mapper;

import java.util.UUID;

import org.apache.ibatis.annotations.Mapper;

import com.gyubanco.core.modules.transfer.model.Transfer;

@Mapper
public interface TransferMapper {

  Transfer insertTransfer(Transfer transfer);

  Transfer getByRequestId(UUID requestId);

}
