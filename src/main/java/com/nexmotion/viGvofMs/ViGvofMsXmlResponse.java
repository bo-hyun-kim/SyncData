package com.nexmotion.viGvofMs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.nexmotion.xmlUtil.XmlUtil;

@Component
public class ViGvofMsXmlResponse {

  @Autowired
  private ViGvofMsService viGvofMsService;
  
  public void ViGvofMsXmlres() {
    
    try {

      String filePath = "src/main/resources/xml/testxml.xml";

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder doc = factory.newDocumentBuilder();
      Document parser = doc.parse(filePath);
      List<ViGvofMs> viGvofMsList = new ArrayList<>();

      // MARK: - PAGE
			for (Node page : XmlUtil.asList(parser.getElementsByTagName("PAGE"))) {
				String value = page.getNodeName();
				if (value.equals("PAGE")) {
					System.out.println("PAGE:" + page.getTextContent());
				}
			}

			// MARK: - CHG_START_DTTM
			for (Node chgStartDttm : XmlUtil.asList(parser.getElementsByTagName("CHG_START_DTTM"))) {
				String value = chgStartDttm.getNodeName();
				if (value.equals("CHG_START_DTTM")) {
					System.out.println("CHG_START_DTTM:" + chgStartDttm.getTextContent());
				}
			}

			// MARK: - CHG_END_DTTM
			for (Node chgEndDttm : XmlUtil.asList(parser.getElementsByTagName("CHG_END_DTTM"))) {
				String value = chgEndDttm.getNodeName();
				if (value.equals("CHG_END_DTTM")) {
					System.out.println("CHG_END_DTTM:" + chgEndDttm.getTextContent());
				}
			}

      // MARK - LOOP
      for (Node loop : XmlUtil.asList(parser.getElementsByTagName("LOOP"))) {
				NodeList cList = loop.getChildNodes();
				
				// MARK: - RECORD
				for (Node record: XmlUtil.asList(cList)) {
					NodeList data = record.getChildNodes();
					ViGvofMs viGvofMs = new ViGvofMs();

					for (Node rdata: XmlUtil.asList(data)) {
						String value = rdata.getNodeName();
						if (value.equals("#text")) continue;
						if (value.equals("gvofcode")) {
							// System.out.println("gvofcode: " + rdata.getTextContent());
							viGvofMs.setGvofcode(rdata.getTextContent());
						}
						if (value.equals("gvofname")) {
							// System.out.println("gvofname:" + rdata.getTextContent());
							viGvofMs.setGvofname(rdata.getTextContent());
						}
						if (value.equals("hgvofcode")) {
							// System.out.println("hgvofcode: " + rdata.getTextContent());
							viGvofMs.setHgvofcode(rdata.getTextContent());
						}
						if (value.equals("useyn")) {
							// System.out.println("useyn: " + rdata.getTextContent());
							viGvofMs.setUseyn(rdata.getTextContent());
						}
						if (value.equals("chgdate")) {
							// System.out.println("chgdate: " + rdata.getTextContent());
							viGvofMs.setChgdate(rdata.getTextContent());
            }
						viGvofMsList.add(viGvofMs);
					}
				}
			}
      viGvofMsService.insertViGvofMs(viGvofMsList);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
