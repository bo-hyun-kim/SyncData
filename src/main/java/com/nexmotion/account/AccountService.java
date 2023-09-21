package com.nexmotion.account;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements AccountMapper {

	@Autowired
	private AccountMapper accountMapper;

	@Override
	public List<Account> selectAccount() throws SQLException {
		return accountMapper.selectAccount();
	}
	
	@Override
	public void insertAccountList(List<Account> list) throws SQLException {
		accountMapper.insertAccountList(list);
	}

	@Override
	public void insertUseridAuthList(List<Account> list) throws SQLException {
		accountMapper.insertUseridAuthList(list);
	}

	@Override
	public void insertAccount(Account account) throws SQLException {
		accountMapper.insertAccount(account);
	}

	@Override
	public void insertUseridAuth(Account account) throws SQLException {
		accountMapper.insertUseridAuth(account);
	}

	@Override
	public void updateAccount(Account account) throws SQLException {
		accountMapper.updateAccount(account);
	}

	@Override
	public void truncateAccount() throws SQLException {
		accountMapper.truncateAccount();
	}

	@Override
	public void truncateUseridAuth() throws SQLException {
		accountMapper.truncateUseridAuth();
	}

	@Override
	public void deleteRetireAccount(Account account) throws SQLException {
		accountMapper.deleteRetireAccount(account);
	}
	
	@Override
	public void deleteRetireUseridAuth(Account account) throws SQLException {
		accountMapper.deleteRetireUseridAuth(account);
	}

	@Override
	public void updateAuth(Account account) throws Exception {
		accountMapper.updateAuth(account);
	}
}