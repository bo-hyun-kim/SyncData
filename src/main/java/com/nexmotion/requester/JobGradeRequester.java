package com.nexmotion.requester;

import org.springframework.stereotype.Component;

@Component
public class JobGradeRequester extends AbstractRequester{
    @Override
    public int getQryCl() {
        return 5;
    }

    @Override
    public String getQryPups() {
        return "직급 데이터 동기화";
    }

    @Override
    public void parse(String response) throws Exception {
        return;
    }
}
