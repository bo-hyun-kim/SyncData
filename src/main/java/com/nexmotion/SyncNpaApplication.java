package com.nexmotion;

import com.nexmotion.sync.SyncData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nexmotion.requester.OrganizationRequester;
import com.nexmotion.requester.UserRequester;

@SpringBootApplication
public class SyncNpaApplication implements CommandLineRunner {

	private final Logger LOGGER = LoggerFactory.getLogger(SyncNpaApplication.class);

	@Autowired
	private SyncData syncData;

	public static void main(String[] args) {
		SpringApplication.run(SyncNpaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			syncData.sync();
		} catch(Exception e) {
			LOGGER.error("ERROR_sync()", e);
		}

//		if (!organizationRequester.run()) {
//			System.err.println("조직정보 동기화 실패!");
//			return;
//		}
//
//		if (!userRequester.run()) {
//			System.err.println("사용자정보 동기화 실패!");
//			return;
//		}
	}
}
