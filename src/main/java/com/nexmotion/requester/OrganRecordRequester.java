package com.nexmotion.requester;

import org.springframework.stereotype.Component;

@Component
public class OrganRecordRequester extends AbstractRequester{
    @Override
    public int getQryCl() {
        return 7;
    }

    @Override
    public String getQryPups() {
        return "조직이력 데이터 동기화";
    }
}
