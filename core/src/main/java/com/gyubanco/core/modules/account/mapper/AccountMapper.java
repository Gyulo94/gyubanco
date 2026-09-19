package com.gyubanco.core.modules.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gyubanco.core.modules.account.model.Account;

@Mapper
public interface AccountMapper {

  Account insertAccount(Account account);

  Long getNextAccountNumberSequence();

  Account getById(Long accountId);

  int transferBalance(
      @Param("depositAccountId") Long depositAccountId,
      @Param("withdrawalAccountId") Long withdrawalAccountId,
      @Param("amount") Long amount);

}
