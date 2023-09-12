package com.nexmotion.organ;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.nexmotion.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.nexmotion.xmlUtil.XmlUtil;
import org.xml.sax.InputSource;

@Component
public class ParseOrganXML {

  @Autowired
  private OrganService organService;

	public void parseOrganData(String parameter) {

		try {
			String rData = parameter;
			System.err.println("rdata===>" + rData);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(rData)));
			Element rootElement = doc.getDocumentElement();

			String respCd = rootElement.getElementsByTagName("RESP_CD").item(0).getTextContent();

			// 00 : 응답데이터 있고 추가 데이터 없는 경우
			// 01 : 응답 데이터가 아예 없는 경우
			// 02 : 응답데이터 있고 추가 데이터 있는 경우
			// 그 외 : 에러인 경우

			if (respCd.equals("01")) {
				return;
			}

			// 에러 향후 재처리
			if (!respCd.equals("02") && !respCd.equals("00")) {
				String errorMessage = String.format("%s", respCd.toString());
				throw new Exception(errorMessage);
			}

			parseData(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseData(Document doc) throws Exception {

	}

}
