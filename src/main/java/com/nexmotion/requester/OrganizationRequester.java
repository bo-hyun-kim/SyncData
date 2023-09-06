package com.nexmotion.requester;

import org.springframework.stereotype.Component;

@Component
public class OrganizationRequester extends AbstractRequester {

	@Override
	public int getQryCl() {
		// 조직
		return 3;
	}

	@Override
	public String getQryPups() {
		return "조직정보동기화테스트";
	}

}
