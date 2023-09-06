package com.nexmotion.account;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.nexmotion.xmlUtil.XmlUtil;

@Component
public class AccountXmlResponse {
  
   @Autowired
  private AccountService accountService;

  public void AccountXmlres(String parameter) {

	try {
		String rData = parameter;
		System.err.println("rdata===>" + rData);
  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document parser = builder.parse(new InputSource(new StringReader(rData)));
		List<Account> accountList = new ArrayList<>();
		
		// MARK: - PAGE
		for (Node page : XmlUtil.asList(parser.getElementsByTagName("PAGE"))) {
		  String value = page.getNodeName();
		  if (value.equals("PAGE")) {
			System.err.println("PAGE:" + page.getTextContent());
		  }
		}
  
			// MARK: - CHG_START_DTTM
			for (Node chgStartDttm : XmlUtil.asList(parser.getElementsByTagName("CHG_START_DTTM"))) {
				String value = chgStartDttm.getNodeName();
				if (value.equals("CHG_START_DTTM")) {
					System.err.println("CHG_START_DTTM:" + chgStartDttm.getTextContent());
				}
			}

			// MARK: - CHG_END_DTTM
			for (Node chgEndDttm : XmlUtil.asList(parser.getElementsByTagName("CHG_END_DTTM"))) {
				String value = chgEndDttm.getNodeName();
				if (value.equals("CHG_END_DTTM")) {
					System.err.println("CHG_END_DTTM:" + chgEndDttm.getTextContent());
				}
			}

			 for (Node loop : XmlUtil.asList(parser.getElementsByTagName("LOOP"))) {
        NodeList cList = loop.getChildNodes();
        
        // MARK: - RECORD
        for (Node record: XmlUtil.asList(cList)) {
          NodeList data = record.getChildNodes();
          Account account = new Account();

          for (Node rdata: XmlUtil.asList(data)) {
            String value = rdata.getNodeName();
            if (value.equals("#text")) continue;
            if (value.equals("USER_ID")) {
              System.err.println("userid: " + rdata.getTextContent());
              account.setUserid(rdata.getTextContent());
            }
            if (value.equals("USER_NO")) {
              System.err.println("userno:" + rdata.getTextContent());
              account.setUserno(rdata.getTextContent());
            }
            if (value.equals("USER_NM")) {
              System.err.println("username: " + rdata.getTextContent());
              account.setUsername(rdata.getTextContent());
            }
            if (value.equals("USER_GVOF_CD")) {
              System.err.println("gvofcode: " + rdata.getTextContent());
              account.setGvofcode(rdata.getTextContent());
            }
            if (value.equals("USER_OPOS_CD")) {
              System.err.println("oposcode: " + rdata.getTextContent());
              account.setOposcode(rdata.getTextContent());
            }
            if (value.equals("USER_CPOS_CD")) {
              System.err.println("cposcode: " + rdata.getTextContent());
              account.setCposcode(rdata.getTextContent());
            }
            accountList.add(account);
            System.err.println("account ===> " + (account));
          }
        }
      }
      System.err.println("accountList ===> " + (accountList));
      accountService.insertAccount(accountList);
      System.err.println("accountService ===> " + (accountService));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void AccountXmlAns() {
  }

  public void accountXmlAns() {
  }
}