package com.nexmotion.position;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

  public void parsePositionData(String parameter) {

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

//		List<Account> accountList = new ArrayList<>();
//		// MARK: - PAGE
//		for (Node page : XmlUtil.asList(doc.getElementsByTagName("PAGE"))) {
//			String value = page.getNodeName();
//			if (value.equals("PAGE")) {
//				System.err.println("PAGE:" + page.getTextContent());
//			}
//		}
//
//		// MARK: - CHG_START_DTTM
//		for (Node chgStartDttm : XmlUtil.asList(doc.getElementsByTagName("CHG_START_DTTM"))) {
//			String value = chgStartDttm.getNodeName();
//			if (value.equals("CHG_START_DTTM")) {
//				System.err.println("CHG_START_DTTM:" + chgStartDttm.getTextContent());
//			}
//		}
//
//		// MARK: - CHG_END_DTTM
//		for (Node chgEndDttm : XmlUtil.asList(doc.getElementsByTagName("CHG_END_DTTM"))) {
//			String value = chgEndDttm.getNodeName();
//			if (value.equals("CHG_END_DTTM")) {
//				System.err.println("CHG_END_DTTM:" + chgEndDttm.getTextContent());
//			}
//		}
//
//		for (Node loop : XmlUtil.asList(doc.getElementsByTagName("LOOP"))) {
//			NodeList cList = loop.getChildNodes();
//
//			// MARK: - RECORD
//			for (Node record: XmlUtil.asList(cList)) {
//				NodeList data = record.getChildNodes();
//				Account account = new Account();
//
//				for (Node rdata: XmlUtil.asList(data)) {
//					String value = rdata.getNodeName();
//					if (value.equals("#text")) continue;
//					if (value.equals("USER_ID")) {
//						System.err.println("userid: " + rdata.getTextContent());
//						account.setUserid(rdata.getTextContent());
//					}
//					if (value.equals("USER_NO")) {
//						System.err.println("userno:" + rdata.getTextContent());
//						account.setUserno(rdata.getTextContent());
//					}
//					if (value.equals("USER_NM")) {
//						System.err.println("username: " + rdata.getTextContent());
//						account.setUsername(rdata.getTextContent());
//					}
//					if (value.equals("USER_GVOF_CD")) {
//						System.err.println("gvofcode: " + rdata.getTextContent());
//						account.setGvofcode(rdata.getTextContent());
//					}
//					if (value.equals("USER_OPOS_CD")) {
//						System.err.println("oposcode: " + rdata.getTextContent());
//						account.setOposcode(rdata.getTextContent());
//					}
//					if (value.equals("USER_CPOS_CD")) {
//						System.err.println("cposcode: " + rdata.getTextContent());
//						account.setCposcode(rdata.getTextContent());
//					}
//					accountList.add(account);
//					System.err.println("account ===> " + (account));
//				}
//			}
//		}
//		organService.insertAccount(accountList);
  }

//
//  public void ViCnmCdXmlres() {
//
//    try {
//      String filePath = "src/main/resources/xml/testxml.xml";
//
//      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//      DocumentBuilder doc = factory.newDocumentBuilder();
//      Document parser = doc.parse(filePath);
//      List<Position> positionList = new ArrayList<>();
//
//      // MARK: - PAGE
//      for (Node page : XmlUtil.asList(parser.getElementsByTagName("PAGE"))) {
//        String value = page.getNodeName();
//        if (value.equals("PAGE")) {
//          System.out.println("PAGE: " + page.getTextContent());
//        }
//      }
//
//      // MARK: - CHG_START_DTTM
//      for (Node chgStartDttm : XmlUtil.asList(parser.getElementsByTagName("CHG_START_DTTM"))) {
//        String value = chgStartDttm.getNodeName();
//        if (value.equals("CHG_START_DTTM")) {
//          System.out.println("CHG_START_DTTM:" + chgStartDttm.getTextContent());
//        }
//      }
//
//      // MARK: - CHG_END_DTTM
//      for (Node chgEndDttm : XmlUtil.asList(parser.getElementsByTagName("CHG_END_DTTM"))) {
//        String value = chgEndDttm.getNodeName();
//        if (value.equals("CHG_END_DTTM")) {
//          System.out.println("CHG_END_DTTM:" + chgEndDttm.getTextContent());
//        }
//      }
//
//      // MARK - LOOP
//      for (Node loop : XmlUtil.asList(parser.getElementsByTagName("LOOP"))) {
//        NodeList cList = loop.getChildNodes();
//
//        // MARK: - RECORD
//        for (Node record : XmlUtil.asList(cList)) {
//          NodeList data = record.getChildNodes();
//          Position position = new Position();
//
//          for (Node rdata : XmlUtil.asList(data)) {
//            String value = rdata.getNodeName();
//            if (value.equals("#text")) continue;
//            if (value.equals("grpcode")) {
//              System.out.println("grpcode: " + rdata.getTextContent());
//              position.setGrpcode(rdata.getTextContent());
//            }
//            if (value.equals("cmdcode")) {
//              System.out.println("cmdcode:" + rdata.getTextContent());
//              position.setCmdcode(rdata.getTextContent());
//            }
//            if (value.equals("cmdcodename")) {
//              System.out.println("cmdcodename: " + rdata.getTextContent());
//              position.setCmdcodename(rdata.getTextContent());
//            }
//            if (value.equals("chgdate")) {
//              System.out.println("chgdate: " + rdata.getTextContent());
//              position.setChgdate(rdata.getTextContent());
//            }
//            if (value.equals("useyn")) {
//              System.out.println("useyn: " + rdata.getTextContent());
//              position.setUseyn(rdata.getTextContent());
//            }
//            positionList.add(position);
//          }
//        }
//      }
//      viCnmCdService.insertViCnmCd(positionList);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
}
