package com.nexmotion.requester;

import org.springframework.stereotype.Component;

@Component
public class PositionRequester extends AbstractRequester{
    @Override
    public int getQryCl() {
        return 4;
    }

    @Override
    public String getQryPups() {
        return "직위 데이터 동기화";
    }
}
