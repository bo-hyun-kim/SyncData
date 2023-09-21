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
import org.springframework.scheduling.annotation.Scheduled;

import java.io.PrintWriter;
import java.io.StringWriter;

@SpringBootApplication
@EnableScheduling
public class SyncNpaApplication implements CommandLineRunner {

	private final Logger debugLogger = LoggerFactory.getLogger("checkDebug");

	@Autowired
	private SyncData syncData;

	public static void main(String[] args) {
		SpringApplication.run(SyncNpaApplication.class, args);
	}
	@Override
	public void run(String... args) {
		debugLogger.debug("SyncNpa 시작");
		syncData.sync();
		debugLogger.debug("SyncNpa 종료");
	}
}
