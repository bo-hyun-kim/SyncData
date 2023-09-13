package com.nexmotion.requester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexmotion.organ.ParseOrganXML;

@Component
public class OrganizationRequester extends AbstractRequester {
	
	@Autowired
	ParseOrganXML parseOrganXML;
	
	@Override
	public int getQryCl() {
		// 조직
		return 3;
	}

	@Override
	public String getQryPups() {
		return "조직정보동기화테스트";
	}

	@Override
	public void parse(String response) throws Exception {
		parseOrganXML.parseOrganData(response);
	}

}
