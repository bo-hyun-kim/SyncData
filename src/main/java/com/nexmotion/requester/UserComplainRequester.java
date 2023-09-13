package com.nexmotion.requester;

import org.springframework.stereotype.Component;

@Component
public class UserComplainRequester extends AbstractRequester{
    @Override
    public int getQryCl() {
        return 2;
    }

    @Override
    public String getQryPups() {
        return "사용자 기본(민원) 데이터 동기화";
    }

    @Override
    public void parse(String response) throws Exception {
        return;
    }
}
