package com.nexmotion.requester;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.nexmotion.account.ParseAccountXML;
import com.nexmotion.organ.ParseOrganXML;
import com.nexmotion.position.ParsePositionXML;
import com.util.crypto.SEEDCryptography;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpStatus;
import java.net.URI;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.format.DateTimeFormatter;

@Component
public abstract class AbstractRequester {

	private final Logger logger = LoggerFactory.getLogger("checkDebug");
	
	@Value("${server.addr}")
	private String serverAddr;
	
	@Value("${reqst.sys.cd}")
	private String reqstSysCd;

	@Value("${auth.key}")
	private String authKey;
	
	@Value("${blg.agfc.gvofcd}")
	private String blgAgfcGvofCd;

	@Value("${encryption.key}")
	private String encryptionKey;

	protected AbstractRequester() {
	}

	// 조회 구분 반환
	public abstract int getQryCl();

	// 조회시스템명 반환
	public abstract String getQryPups();
	
	// 파싱 및 DB 저장
	public abstract void parse(String response) throws Exception;
	
	// 요청페이지(최초요청에서는 1, 응답코드가 02(다음자료있음)일 때 다음페이지 입력)
	private int page = 1;
	
	// 요청구분(0:최초요청, 1:추가요청)
	int reqCl = 0;		
	
	//조회시스템명
	String qryPups = getQryPups();
	
	// 조회하려는 수정일시의 시작시간 (YYYYMMDDHH24MISS)
	LocalDateTime chgStartDttm = null;

	// 조회하려는 수정일시의 종료시간 (YYYYMMDDHH24MISS)
	LocalDateTime chgEndDttm = null;
	
	/**
	 * DB 에서 조회하려는 시스템의 시간을 불러온다. 
	 */
//	public void loadSystemDate() {
//		// DB 에서 가져옴
//		this.chgStartDttm = "20181120100000";
//		//this.chgEndDttm = 현재시간으로 변경
//		this.chgEndDttm = "현재시간";
//	}
	
//	public void saveSystemDate() {
		// db 에 현재 객체가 가진 chgEndDttm 시간을 chgStartDttm 으로 업데이트 한다.
		// dbsave(this.chgEndDttm);
//	}
	
	public RequestDTO getRequestDTO() {
		logger.debug("getRequestDTO() 시작");
		RequestDTO dto = new RequestDTO();
		dto.setQryPups(this.qryPups);
		dto.setReqCl(this.reqCl);
		dto.setPage(this.page);
		dto.setChgStartDttm(this.chgStartDttm);
		dto.setChgEndDttm(this.chgEndDttm);
		logger.debug("getRequestDTO() 종료");
		return dto;
	}
	
//	void initVariable() {
//		loadSystemDate();
//	}
	
	/**
	 * 공통 헤더 작성
	 * 
	 * @param qryPups : 조회시스템명
	 * @return Map<String, String> 헤더 객체
	 */
	private Map<String, String> getHeader(String qryPups) {
		
		logger.debug("getHeader() 시작");
		Map<String, String> reqHeader = new LinkedHashMap<String, String>();

		reqHeader.put("TRNS_ID", getTodayTimeMilli() + "12345");
		reqHeader.put("TLGR_CD", "PN02");
		reqHeader.put("REQST_ENC_CD", "UTF-8");
		reqHeader.put("REQST_DTTM", getTodayTimeMilli());
		reqHeader.put("REQST_SYS_CD", reqstSysCd); // 기관아이디
		reqHeader.put("AUTH_KEY", authKey); // 인증키
		reqHeader.put("QRY_PUPS", new String(Base64.encodeBase64(qryPups.getBytes())));
		reqHeader.put("RSPSE_CD", "");
		System.out.println("reqHeader: "+ reqHeader);
		logger.debug("getHeader() 종료");
		return reqHeader;
	}

	private String getData(RequestDTO dto) {
		logger.debug("getData() 시작");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		int reqCl = dto.getReqCl();
		int qryCl = getQryCl();
		int page = dto.getPage();
		LocalDateTime chgStartDttm = dto.getChgStartDttm();
		System.err.println("널 파라미터 확인===>" + chgStartDttm.format(formatter));
		LocalDateTime chgEndDttm = dto.getChgEndDttm();
		System.err.println("널 파라미터 확인===>" + chgEndDttm);
		String fmt = "<REQUEST><HEADER><TLGR_CD>PN02</TLGR_CD><REQ_CL>%d</REQ_CL><QRY_CL>%d</QRY_CL>"
				+ "<RESP_CD /><REQ_CNT /></HEADER><DATA><PAGE>%d</PAGE><CHG_START_DTTM>%s</CHG_START_DTTM>"
				+ "<CHG_END_DTTM>%s</CHG_END_DTTM><BLG_AGFC_GVOF_CD>%s</BLG_AGFC_GVOF_CD></DATA></REQUEST>";
		String xml = String.format(fmt, reqCl, qryCl, page, chgStartDttm.format(formatter), chgEndDttm.format(formatter), blgAgfcGvofCd);
		System.err.println("xml 확인===>" + xml);
		logger.debug("getData() 종료");
		return xml;
	}
	
	private String getTodayTimeMilli() {
		logger.debug("getTodayTimeMilli() 시작");
		Format format = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US);
		String todayStr = format.format(new Date());
		logger.debug("getTodayTimeMilli() 종료");
		return todayStr;
	}

	public boolean run(LocalDateTime startDt, LocalDateTime endDt) throws Exception {
//		initVariable();
//		System.err.println("시간 파라미터 확인===>"+ startDt + endDt);
		logger.debug("run() 시작");
		this.chgStartDttm = endDt;
		this.chgEndDttm = startDt;
		RequestDTO dto = getRequestDTO();
//		System.err.println("dto확인 ===>"+ dto);
		String respCd = null;

		do {
			logger.debug("do while 구문 시작");
			String response = this.send(dto);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(response)));
			Element rootElement = doc.getDocumentElement();

			respCd = rootElement.getElementsByTagName("RESP_CD").item(0).getTextContent();
			String loggerRespcd = String.format("%s", respCd);

			// 실제 db 에 response 결과값을 파싱해서 저장
			this.parse(response);
			
			if (!respCd.equals("02")) {
				logger.debug("respCd가 02가 아니다 ");
				break;
			}
			
			dto.setReqCl(1);
			dto.setPage(dto.getPage() + 1);
		} while(true);
		
		// db 에 현재시각 기록
//		saveSystemDate();
		logger.debug("run() 종료");
		return true;
		}
	
	/**
	 * 사용자 계정정보, 조직정보등의 페이지를 조회할 때 사용한다.
	 * 성공시 요청한 정보에 대해 xml 형태로 데이터가 전달된다.
	 *
	 * @return 
	 * @throws Exception
	 */
	private String send(RequestDTO dto) throws Exception {
		logger.debug("send() 시작");
		String ret;

		Map<String, String> reqHeader = getHeader(dto.getQryPups());
		String reqBody = getData(dto);

		SEEDCryptography seedCrypto = new SEEDCryptography(encryptionKey); // 암호화키

		byte[] result = request(seedCrypto.encrypt(reqBody.getBytes()), reqHeader);
		System.out.println("복호화 전 데이터===>" + new String(result, StandardCharsets.UTF_8));
		byte[] decytedResponse = seedCrypto.decrypt(result);
		System.out.println("복호화 후 데이터==>" + new String(decytedResponse));

		ret = new String(decytedResponse, StandardCharsets.UTF_8);
		ret = ret.trim();
		logger.debug("send() 종료");
		return ret;
	}

	public byte[] request(byte[] bodyEntity, Map<String, String> reqHeader) throws Exception {
		
		logger.debug("request() 시작");
		byte[] result;

		// Timeout 설정
		int timeoutMilliseconds = 5000; // 5초

		// RequestConfig를 사용하여 timeout 설정
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(timeoutMilliseconds)
				.setSocketTimeout(timeoutMilliseconds)
				.build();

		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.build();

//		CloseableHttpClient httpClient = HttpClients.createDefault();
            // URI 생성
		URI uri = new URI(serverAddr);
		HttpPost postMethod = new HttpPost(uri);
		postMethod.setHeader("Content-Type", "application/xml; charset=UTF-8");
		HttpEntity entity = new ByteArrayEntity(bodyEntity);
		postMethod.setEntity(entity);
		for (String key : reqHeader.keySet()) {
			postMethod.addHeader(key, reqHeader.get(key));
		}
		HttpResponse response = httpClient.execute(postMethod);
		result = FileCopyUtils.copyToByteArray(response.getEntity().getContent());
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			throw new Exception("Http Error:" + response.getStatusLine().getStatusCode());
		httpClient.close();

	
		logger.debug("request() 종료");
		return result;
	}

}
