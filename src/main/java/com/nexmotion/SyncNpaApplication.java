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

	@Autowired
	private UserRequester userRequester;

	@Autowired
	private OrganizationRequester organizationRequester;

	public static void main(String[] args) {
		SpringApplication.run(SyncNpaApplication.class, args);
	}

	@Override
	public void run(String... args) {

		try {
			syncData.sync();
		} catch (Exception e) {
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
//	@Transactional
//	public void sync() throws Exception {
//		SyncVO syncvo = new SyncVO();
//		List<SyncVO> dateInfo = new ArrayList<>();
//		dateInfo = syncService.getChgDate();
//
//		LocalDateTime startDt = dateInfo.get(0).getChgstartdate();
//
//		if (startDt == null) {
//			LOGGER.info("truncate all tables");
//			syncService.truncateAccount();
//			syncService.truncateOraganization();
//			startDt = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
//		}
//		LocalDateTime endDt = LocalDateTime.now();
//		organizationRequester.run(startDt, endDt);
//		userRequester.run(startDt, endDt);
////		organizationSync(startDt, endDt);
////		userSync(startDt, endDt);
//		syncvo.setChgenddate(endDt);
//		syncService.updateChgDate(syncvo);
//	}
}
