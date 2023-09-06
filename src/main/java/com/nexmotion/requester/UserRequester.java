package com.nexmotion.requester;

import org.springframework.stereotype.Component;

@Component
public class UserRequester extends AbstractRequester {

	@Override
	public int getQryCl() {
		// 사용자 기본
		return 1;
	}

	@Override
	public String getQryPups() {
		return "사용자조직정보조회";
	}

}
