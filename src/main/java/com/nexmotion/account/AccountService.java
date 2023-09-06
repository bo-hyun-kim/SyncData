package com.nexmotion.account;

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
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public void insertAccount(List<Account> list) throws Exception {
		accountMapper.insertAccount(list);
	}

}