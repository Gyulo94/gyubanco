package com.gyubanco.core.modules.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gyubanco.core.modules.account.model.Account;

@Mapper
public interface AccountMapper {

  Account insertAccount(Account account);

  Long getNextAccountNumberSequence();

}
