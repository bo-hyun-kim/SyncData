package com.util.http;

import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.FileCopyUtils;

public class Requester implements  InitializingBean {

	private String requestAdress;

	private HttpClient httpClient;

	public Requester(String requestAdress) throws Exception {
		setRequestAdress(requestAdress);
		afterPropertiesSet();
	}

	public byte[] request(byte[] bodyEntity, Map<String, String> reqHeader) throws Exception {
		
		try{
			HttpPost postMethod = new HttpPost(getRequestAdress());
			HttpEntity entity = new ByteArrayEntity(bodyEntity);
			postMethod.setEntity(entity);
			for(String key: reqHeader.keySet()){
				postMethod.addHeader(key, reqHeader.get(key));
			}
			HttpResponse response = httpClient.execute(postMethod);
			
			/*for(Header header : response.getAllHeaders()){
				System.out.println("name : " + header.getName() + ", value : " + header.getValue());
			}*/
			
			byte[] result = FileCopyUtils.copyToByteArray(response.getEntity().getContent()); 
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				return result;
			else
				return null;
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	public String getRequestAdress() {
		return requestAdress;
	}

	public void setRequestAdress(String requestAdress) {
		this.requestAdress = requestAdress;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		httpClient= HttpClients.createDefault();
	}
}
