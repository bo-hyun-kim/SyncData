package com.nexmotion.requester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexmotion.position.ParsePositionXML;

@Component
public class PositionRequester extends AbstractRequester{
	
	@Autowired
	ParsePositionXML parsePositionXML;
	
    @Override
    public int getQryCl() {
        return 4;
    }

    @Override
    public String getQryPups() {
        return "직위 데이터 동기화";
    }

	@Override
	public void parse(String response) throws Exception {
		parsePositionXML.parsePositionData(response);
	}
}
