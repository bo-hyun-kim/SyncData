package com.nexmotion.account;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
  
  public void insertAccountList(List<Account> list) throws Exception;

  public void insertAccount(Account account) throws Exception;

  public List<Account> selectAccount() throws Exception;

  public void updateAccount(Account account) throws Exception;
}
