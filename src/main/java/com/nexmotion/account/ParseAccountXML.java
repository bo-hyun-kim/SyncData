package com.nexmotion.account;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.nexmotion.code.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

@Component
public class ParseAccountXML {

    @Autowired
    private AccountService accountService;

    private final Logger logger = LoggerFactory.getLogger("com.nexmotion.Account");

    //리턴값 : false - 추가 요청 정지
    //리턴값 : true - 추가 요청

    public void parseAccountData(String parameter) throws Exception {

            ErrorCode errorcode = new ErrorCode();

            String rData = parameter;
            System.err.println("accountData===>" + rData);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(rData)));
            Element rootElement = doc.getDocumentElement();

            String respCd = rootElement.getElementsByTagName("RESP_CD").item(0).getTextContent();

            List<Account> existingData = accountService.selectAccount();
            System.err.println("list==>" + existingData);
            // 00 : 응답데이터 있고 추가 데이터 없는 경우
            // 01 : 응답 데이터가 아예 없는 경우
            // 02 : 응답데이터 있고 추가 데이터 있는 경우
            // 그 외 : 에러인 경우
            if (respCd.equals("01")) {
                return;
            }

            // 에러 향후 재처리
            if (!(respCd.equals("02")) && !(respCd.equals("00"))) {
                String errorMessage = String.format("%s:%s", respCd, errorcode.get(respCd));
                throw new Exception(errorMessage);
            }

            List<Account> newData = parseData(doc);

            if (existingData.size() == 0) {
                accountService.insertAccountList(newData);
                accountService.insertUseridAuthList(newData);
            } else {
                compareData(newData, existingData);
            }
    }

    public List<Account> parseData(Document doc) throws Exception {

        List<Account> accountList = new ArrayList<>();

        Element responseElement = doc.getDocumentElement();
        Element headerElement = (Element) responseElement.getElementsByTagName("HEADER").item(0);
        String tlgrCd = getElementText(headerElement, "TLGR_CD");
        String respCd = getElementText(headerElement, "RESP_CD");
        Element dataElement = (Element) responseElement.getElementsByTagName("DATA").item(0);
        NodeList recordList = dataElement.getElementsByTagName("RECORD");
        for (Node recordNode : asList(recordList)) {
            Account account = new Account();
            Element recordElement = (Element) recordNode;
            String userNo = getElementText(recordElement, "USER_NO");
            String userId = getElementText(recordElement, "USER_ID");
            String userNm = getElementText(recordElement, "USER_NM");
            String userGvofCd = getElementText(recordElement, "USER_GVOF_CD");
            String userOposCd = getElementText(recordElement, "USER_OPOS_CD");
            String userCposCd = getElementText(recordElement, "USER_CPOS_CD");
            String userStatCd = getElementText(recordElement, "USER_STAT_CD");
            String chgdate = getElementText(recordElement, "CHG_DTTM");
            account.setUserno(userNo);
            account.setUserid(userId);
            account.setUsername(userNm);
            account.setGvofcode(userGvofCd);
            account.setOposcode(userOposCd);
            account.setCposcode(userCposCd);
            account.setUserstat(userStatCd);
            account.setChgdate(chgdate);
            accountList.add(account);
            System.err.println("account===>"+account);
        }
        System.err.println("accountList===>"+accountList);
        return accountList;
    }

    private String getElementText(Element parentElement, String tagName) {
        logger.debug("getElementText() 시작");
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        logger.debug("getElementText() 종료");
        return null;
    }

    private List<Node> asList(NodeList nodeList) {
        logger.debug("asList() 시작");
        List<Node> nodeListAsList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            nodeListAsList.add(nodeList.item(i));
        }
        logger.debug("asList() 종료");
        return nodeListAsList;
    }

    private void compareData(List<Account> newData, List<Account> existingData) throws Exception {
        logger.debug("compareData() 시작");
        for (Account newAccount : newData) {
            boolean found = false;
            for (Account existingAccount : existingData) {
                if (newAccount.getUserid().equals(existingAccount.getUserid())) {
                    System.err.println("겹치는 데이터" + newAccount);
                    accountService.updateAccount(newAccount);
                    if (!(newAccount.getGvofcode().equals(existingAccount.getGvofcode()))) {
                        accountService.updateAuth(newAccount);
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                accountService.insertAccount(newAccount);
                accountService.insertUseridAuth(newAccount);
            }
        }
        logger.debug("compareData() 시작");
    }
}