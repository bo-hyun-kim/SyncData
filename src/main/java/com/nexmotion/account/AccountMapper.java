package com.nexmotion.account;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {

  public List<Account> selectAccount() throws Exception;
  
  public void insertAccountList(List<Account> list) throws Exception;

  public void insertUseridAuthList(List<Account> list) throws Exception;

  public void insertAccount(Account account) throws Exception;

  public void insertUseridAuth(Account account) throws Exception;

  public void updateAccount(Account account) throws Exception;

  public void deleteRetireAccount(Account account) throws SQLException;

  public void deleteRetireUseridAuth(Account account) throws SQLException;

  public void truncateAccount() throws SQLException;

  public void truncateUseridAuth() throws SQLException;

}
