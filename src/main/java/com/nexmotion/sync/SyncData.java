package com.nexmotion.sync;

import com.nexmotion.account.Account;
import com.nexmotion.account.AccountService;
import com.nexmotion.organ.OrganService;
import com.nexmotion.position.PositionService;
import com.nexmotion.requester.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.core.Local;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class SyncData {

    private final Logger logger = LoggerFactory.getLogger(SyncData.class);

    @Autowired
    private SyncService syncService;

    @Autowired
    private UserRequester userRequester;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrganizationRequester organizationRequester;

    @Autowired
    private  PositionRequester positionRequester;

    @Transactional(rollbackFor = Exception.class)
    public void sync() throws Exception {

        logger.debug("sync() 시작");
        SyncVO sync = new SyncVO();
        List<SyncVO> dateInfo = syncService.getChgDate();
        LocalDateTime startDt = dateInfo.get(0).getChgStartDate();

        if (startDt == null) {
            logger.debug("startDt가 널인 경우 모든 데이터 삭제 후 데이터를 새로 받는다");
        	// 처음 데이터를 가져오는 경우 모든 데이터를 삭제한다
            //실제로 지금 계정 테이블이랑 조직 테이블 샘플 데이터가 truncate 되면 안되니까 일단 주석처리 해놓음
//            accountService.truncateAccount();
//            organService.truncateOragan();
//            positionService.truncatePosition();
//            syncService.truncateUseridAuth();
            startDt = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        }

        LocalDateTime endDt = LocalDateTime.now();
        sync.setChgEndDate(endDt);

        userRequester.run(startDt, endDt);
//            userComplainRequester.run(startDt, endDt, 1);
        organizationRequester.run(startDt, endDt);
//            organRecordRequester.run(startDt, endDt, 2);
        positionRequester.run(startDt, endDt);
//            jobGradeRequester.run(startDt, endDt, 3);
//            commonCodeRequester.run(startDt, endDt, 3);

        // return 한 값으로 에러 처리 필요

        endDt = endDt.plusSeconds(1);

        sync.setChgStartDate(endDt);
        syncService.updateChgDate(sync);

        LocalDate now = LocalDate.now();

        deleteRetireUser(now);
        logger.debug("end sync()");
    }

    public void deleteRetireUser(LocalDate now) {
        logger.debug("deleteRetireUser() 시작");
        if (now.getDayOfYear() == 256) { //9월13일로 설정. 1로 설정시 1월1일
            logger.debug("삭제기간 설정일과 현재일이 같음");
            // 3년 전으로 이동하고 자정으로 설정
            LocalDate threeYearsAgo = now.minusYears(3).atStartOfDay().toLocalDate();

            // "yyyy-MM-dd" 형식의 문자열로 변환
            String dateToString = threeYearsAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 23:59:59";

            Account account = new Account();
            account.setRetireDate(dateToString);
            try {
                logger.debug("삭제기간 설정일과 현재일이 같은 내용을 삭제.");
                accountService.deleteRetireAccount(account);
                accountService.deleteRetireUseridAuth(account);
            } catch (Exception e) {
                e.printStackTrace();
            //에러 로그 파일에 추가해야함
            }

        }
        logger.debug("deleteRetireUser() 종료");
    }
}
