package com.nexmotion.position;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.nexmotion.account.Account;
import com.nexmotion.code.ErrorCode;
import com.nexmotion.organ.Organ;
import com.nexmotion.organ.ParseOrganXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.nexmotion.xmlUtil.XmlUtil;
import org.xml.sax.InputSource;

@Component
public class ParsePositionXML {

  @Autowired
  private PositionService positionService;

  private final Logger logger = LoggerFactory.getLogger(ParsePositionXML.class);

  public void parsePositionData(String parameter) throws Exception{

    try {
      ErrorCode errorcode = new ErrorCode();

      String rData = parameter;
      System.err.println("positiondata===>" + rData);

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(rData)));
      Element rootElement = doc.getDocumentElement();

      String respCd = rootElement.getElementsByTagName("RESP_CD").item(0).getTextContent();

      List<Position> existingData = positionService.selectPosition();

      // 00 : 응답데이터 있고 추가 데이터 없는 경우
      // 01 : 응답 데이터가 아예 없는 경우
      // 02 : 응답데이터 있고 추가 데이터 있는 경우
      // 그 외 : 에러인 경우

      if (respCd.equals("01")) {
        return;
      }

      // 에러 향후 재처리
      if (!respCd.equals("02") && !respCd.equals("00")) {
        String errorMessage = String.format("%s:%s", respCd, errorcode.get(respCd));
        throw new Exception(errorMessage);
      }

      List<Position> newData = parseData(doc);

      if (existingData.size() == 0) {
        positionService.insertPositionList(newData);
      } else {
        compareData(newData, existingData);
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e);
    }
  }

  public List<Position> parseData(Document doc) throws Exception {
    List<Position> positionList = new ArrayList<>();

    try {
      Element responseElement = doc.getDocumentElement();

      Element headerElement = (Element) responseElement.getElementsByTagName("HEADER").item(0);
      String tlgrCd = getElementText(headerElement, "TLGR_CD");
      String respCd = getElementText(headerElement, "RESP_CD");

      Element dataElement = (Element) responseElement.getElementsByTagName("DATA").item(0);
      NodeList recordList = dataElement.getElementsByTagName("RECORD");

      for (Node recordNode : asList(recordList)) {
        Position position = new Position();
        Element recordElement = (Element) recordNode;
        String positionCnmcode = getElementText(recordElement, "CMN_CD");
        String positionCnmname = getElementText(recordElement, "CMN_CD_NM");
        String chgdate = getElementText(recordElement, "CHG_DTTM");
        String positionGrpcode = getElementText(recordElement, "GRP_CD");
        String positionUseyn = getElementText(recordElement, "CD_USE_YN");

        position.setCnmcode(positionCnmcode);
        position.setCnmname(positionCnmname);
        position.setChgdate(chgdate);
        position.setGrpcode(positionGrpcode);
        position.setUseyn(positionUseyn);

        positionList.add(position);
        System.err.println("position===>"+position);

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return positionList;
  }

  private static String getElementText(Element parentElement, String tagName) {
    NodeList nodeList = parentElement.getElementsByTagName(tagName);
    if (nodeList.getLength() > 0) {
      Node node = nodeList.item(0);
      return node.getTextContent();
    }
    return null;
  }

  private static List<Node> asList(NodeList nodeList) {
    List<Node> nodeListAsList = new ArrayList<>();
    for (int i = 0; i < nodeList.getLength(); i++) {
      nodeListAsList.add(nodeList.item(i));
    }
    return nodeListAsList;
  }

  private void compareData(List<Position> newData, List<Position> existingData) throws Exception {
    for (Position newPosition : newData) {
      boolean found = false;
      for (Position existingPosition : existingData) {
        if (newPosition.getCnmcode().equals(existingPosition.getCnmcode())) {
          System.err.println("겹치는 데이터" + newPosition);
          positionService.updatePosition(newPosition);
          found = true;
          break;
        }
      }
      if (!found) {
        positionService.insertPosition(newPosition);
      }
    }
  }

}
