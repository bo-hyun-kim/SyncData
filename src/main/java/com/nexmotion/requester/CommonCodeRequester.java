package com.nexmotion.requester;

import org.springframework.stereotype.Component;

@Component
public class CommonCodeRequester extends AbstractRequester{
    @Override
    public int getQryCl() {
        return 6;
    }

    @Override
    public String getQryPups() {
        return "공통코드 데이터 동기화";
    }

	@Override
	public void parse(String response) throws Exception {
		// 현재는 사용하지 않음.
		return;
	}
}
