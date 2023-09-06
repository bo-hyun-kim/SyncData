package com.nexmotion.requester;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpStatus;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.nexmotion.account.AccountXmlResponse;

@Component
public abstract class AbstractRequester {
	
	@Value("${server.addr}")
	private String serverAddr;
	
	@Value("${reqst.sys.cd}")
	private String reqstSysCd;

	@Value("${auth.key}")
	private String authKey;
	
	@Value("${blg.agfc.gvofcd}")
	private String blgAgfcGvofCd;

	@Autowired
	AccountXmlResponse accountXmlResponse;
	
	// 조회 구분 반환
	public abstract int getQryCl();

	// 조회시스템명 반환
	public abstract String getQryPups();
	
	// 요청페이지(최초요청에서는 1, 응답코드가 02(다음자료있음)일 때 다음페이지 입력)
	private int page = 1;
	
	// 요청구분(0:최초요청, 1:추가요청)
	int reqCl = 0;		
	
	//조회시스템명
	String qryPups = getQryPups();
	
	// 조회하려는 수정일시의 시작시간 (YYYYMMDDHH24MISS)
	String chgStartDttm = null;
	
	// 조회하려는 수정일시의 종료시간 (YYYYMMDDHH24MISS)
	String chgEndDttm = null;	
	
	/**
	 * DB 에서 조회하려는 시스템의 시간을 불러온다. 
	 */
	public void loadSystemDate() {
		// DB 에서 가져옴
		this.chgStartDttm = "20181120100000";
		//this.chgEndDttm = 현재시간으로 변경
		this.chgEndDttm = "현재시간";
	}
	
	public void saveSystemDate() {
		// db 에 현재 객체가 가진 chgEndDttm 시간을 chgStartDttm 으로 업데이트 한다.
		// dbsave(this.chgEndDttm);
	}
	
	public RequestDTO getRequestDTO() {
		RequestDTO dto = new RequestDTO();
		dto.setQryPups(this.qryPups);
		dto.setReqCl(this.reqCl);
		dto.setPage(this.page);
		dto.setChgStartDttm(this.chgStartDttm);
		dto.setChgEndDttm(this.chgEndDttm);
		return dto;
	}
	
	void initVariable() {
		loadSystemDate();
	}
	
	/**
	 * 공통 헤더 작성
	 * 
	 * @param qryPups : 조회시스템명
	 * @return Map<String, String> 헤더 객체
	 */
	private Map<String, String> getHeader(String qryPups) {
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

		return reqHeader;
	}

	private String getData(RequestDTO dto) {
		int reqCl = dto.getReqCl();
		int qryCl = getQryCl();
		int page = dto.getPage();
		String chgStartDttm = dto.getChgStartDttm();
		String chgEndDttm = dto.getChgEndDttm();
		
		String fmt = "<REQUEST><HEADER><TLGR_CD>PN02</TLGR_CD><REQ_CL>%d</REQ_CL><QRY_CL>%d</QRY_CL>"
				+ "<RESP_CD /><REQ_CNT /></HEADER><DATA><PAGE>%d</PAGE><CHG_START_DTTM>%s</CHG_START_DTTM>"
				+ "<CHG_END_DTTM>%s</CHG_END_DTTM><BLG_AGFC_GVOF_CD>%s</BLG_AGFC_GVOF_CD></DATA></REQUEST>";
		String xml = String.format(fmt, reqCl, qryCl, page, chgStartDttm, chgEndDttm, blgAgfcGvofCd);

		return xml;
	}
	
	private String getTodayTimeMilli() {
		Format format = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US);
		String todayStr = format.format(new Date());
		return todayStr;
	}

	
	public boolean run() {
		initVariable();
		RequestDTO dto = getRequestDTO();
		
		do {
			try {
				String response = this.send(dto);
				accountXmlResponse.AccountXmlres(response);
				// 실제 db 에 response 결과값을 파싱해서 저장
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// 에러로그를 기록
				// DB Rollback
				return false;
			}
			
			if (dto.getPage() > 3)
				break;
			
			dto.setReqCl(1);
			dto.setPage(dto.getPage() + 1);
		} while(true);
		
		// db 에 현재시각 기록
		saveSystemDate();
		
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
		String ret;

		Map<String, String> reqHeader = getHeader(dto.getQryPups());
		String reqBody = getData(dto);

		/*
		 * SEEDCryptography seedCrypto = new
		 * SEEDCryptography("X124214124214214214124124XXXX"); // 암호화키 byte[] result =
		 * requester.request(seedCrypto.encrypt(testXml.getBytes()), reqHeader);
		 */
		byte[] result = request(reqBody.getBytes(), reqHeader);
		ret = new String(result);
		
		return ret;
	}

	public byte[] request(byte[] bodyEntity, Map<String, String> reqHeader) throws Exception {
		byte[] result;
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
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

			for (Header header : response.getAllHeaders()) {
				System.out.println("name : " + header.getName() + ", value : " + header.getValue());
			}

			result = FileCopyUtils.copyToByteArray(response.getEntity().getContent());
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
				throw new Exception("Http Error:" + response.getStatusLine().getStatusCode());
		} catch (IOException e) {
			throw new Exception(e);
		} finally {
		    try {
		        httpClient.close();
		    } catch (IOException e) {
		        // close() 메서드 호출 중 발생하는 예외 처리
		    }
		}
	
		return result;
	}

}
