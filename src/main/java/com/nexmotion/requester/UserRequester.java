package com.nexmotion.requester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexmotion.account.ParseAccountXML;

@Component
public class UserRequester extends AbstractRequester {
	
	@Autowired
	ParseAccountXML parseAccountXML;

	@Override
	public int getQryCl() {
		// 사용자 기본
		return 1;
	}

	@Override
	public String getQryPups() {
		return "사용자기본 데이터 동기화";
	}

	@Override
	public void parse(String response) throws Exception {
		parseAccountXML.parseAccountData(response);
	}

}
