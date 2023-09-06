package com.nexmotion.sendXml;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nexmotion.account.AccountXmlResponse;
import com.nexmotion.requester.RequestDTO;
import com.nexmotion.requester.AbstractRequester;

import org.apache.commons.codec.binary.Base64;
import java.text.Format;
import java.text.SimpleDateFormat;

@Component
public class SendXmlToProxyServer {
	String responseDataString = "";

	@Autowired
	private AccountXmlResponse accountXmlResponse;

	/*
	public boolean AccountXmlAns2() throws IOException {
		URL url = new URL("http://192.168.0.28:8010/handleResponse");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try {

			// HTTP 요청 설정
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setDoOutput(true);

			System.err.println("1");
			//
		 // XML 데이터 전송 try (OutputStream os = connection.getOutputStream()) {
			 // System.err.println("2"); System.err.println("beforexmlBytes" + xmlData);
			 // byte[] xmlBytes = xmlData.getBytes("UTF-8"); System.err.println("3");
			 // System.err.println("xmlBytes" + xmlBytes); os.write(xmlBytes);
			 // System.err.println("4"); }
			 //

			// 응답 처리
			int responseCode = connection.getResponseCode();
			System.err.println("responsecode" + responseCode);
			if (responseCode != HttpURLConnection.HTTP_OK) {
				connection.disconnect(); // 연결 닫기
				return false;
			}

			// 성공적인 응답 처리
			System.err.println("connection ===> " + connection);
			InputStream responseStream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
			System.err.println("reader ===> " + reader);
			StringBuilder responseData = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				responseData.append(line);
				System.err.println("line ===> " + line);
			}

			// responseData에는 응답 데이터가 들어 있습니다.
			responseDataString = responseData.toString();
			System.out.println("Response Data: " + responseDataString);

			// responseStream과 reader를 닫습니다.
			reader.close();
			responseStream.close();
			System.err.println("responseStream ===> " + responseStream);
			// responseStream을 읽어서 필요한 작업 수행
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect(); // 연결 닫기
		}

		System.err.println("responseDataString=" + responseDataString);

		System.err.println("AccountXmlres 직전");
		accountXmlResponse.AccountXmlres(responseDataString);
		System.err.println("AccountXmlres 이후");
	
		return true;
	}
	*/

}