package com.nexmotion;

import com.nexmotion.sync.SyncData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.nexmotion.requester.OrganizationRequester;
import com.nexmotion.requester.UserRequester;

import java.io.PrintWriter;
import java.io.StringWriter;

@SpringBootApplication
@EnableScheduling
public class SyncNpaApplication implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger("SyncNpaApplication.class");

	@Autowired
	private SyncData syncData;

	public static void main(String[] args) {
		SpringApplication.run(SyncNpaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		logger.debug("SyncNpa 시작");
		try {
			syncData.sync();
		} catch (Exception e) {
			e.printStackTrace();
			// StringWriter를 사용하여 스택 트레이스를 문자열로 변환
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTrace = sw.toString();
			logger.debug("main.run()에러 " + e + stackTrace);
			//에러 로그 파일에 추가해야함
		}
		logger.debug("SyncNpa 종료");

	}
}
