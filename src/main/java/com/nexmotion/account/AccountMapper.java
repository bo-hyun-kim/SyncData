package com.nexmotion.account;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {

  public List<Account> selectAccount() throws SQLException;
  
  public void insertAccountList(List<Account> list) throws SQLException;

  public void insertUseridAuthList(List<Account> list) throws SQLException;

  public void insertAccount(Account account) throws SQLException;

  public void insertUseridAuth(Account account) throws SQLException;

  public void updateAccount(Account account) throws SQLException;

  public void deleteRetireAccount(Account account) throws SQLException;

  public void deleteRetireUseridAuth(Account account) throws SQLException;

  public void deleteAccount() throws SQLException;

  public void deleteUseridAuth() throws SQLException;

  public void updateAuth(Account account) throws Exception;
}
