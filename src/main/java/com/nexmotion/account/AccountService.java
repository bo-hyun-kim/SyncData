package com.nexmotion.account;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService implements AccountMapper {

	@Autowired
	private AccountMapper accountMapper;
	
	@Override
	@Transactional
	public void insertAccountList(List<Account> list) throws Exception {
		accountMapper.insertAccountList(list);
	}

	@Override
	@Transactional
	public void insertAccount(Account account) throws Exception {
		accountMapper.insertAccount(account);
	}

	@Override
	@Transactional
	public void insertUseridAuth(Account account) throws Exception {
		accountMapper.insertUseridAuth(account);
	}

	@Override
	public List<Account> selectAccount() throws Exception {
		return accountMapper.selectAccount();
	}

	@Override
	@Transactional
	public void updateAccount(Account account) throws Exception {
		accountMapper.updateAccount(account);
	}
}