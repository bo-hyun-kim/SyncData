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
    private OrganService organService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private OrganizationRequester organizationRequester;

    @Autowired
    private UserComplainRequester userComplainRequester;

    @Autowired
    private CommonCodeRequester commonCodeRequester;

    @Autowired
    private JobGradeRequester jobGradeRequester;

    @Autowired
    private OrganRecordRequester organRecordRequester;

    @Autowired
    private  PositionRequester positionRequester;

    @Transactional(rollbackFor = Exception.class)
    public void sync() throws Exception {

        SyncVO sync = new SyncVO();
        List<SyncVO> dateInfo = new ArrayList<>();

        dateInfo = syncService.getChgDate();

        LocalDateTime startDt = dateInfo.get(0).getChgStartDate();

        if (startDt == null) {
            logger.info("truncate all tables");
            //실제로 지금 계정 테이블이랑 조직 테이블 샘플 데이터가 truncate 되면 안되니까 일단 주석처리 해놓음
//            accountService.truncateAccount();
//            organService.truncateOragan();
//            positionService.truncatePosition();
//              syncService.truncateUseridAuth();
            startDt = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        }

        LocalDateTime endDt = LocalDateTime.now();
        sync.setChgEndDate(endDt);

        try {
            userRequester.run(startDt, endDt, 1);
//            userComplainRequester.run(startDt, endDt, 1);
            organizationRequester.run(startDt, endDt, 2);
//            organRecordRequester.run(startDt, endDt, 2);
            positionRequester.run(startDt, endDt, 3);
//            jobGradeRequester.run(startDt, endDt, 3);
//            commonCodeRequester.run(startDt, endDt, 3);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR_SYNC()");
            throw new Exception(e);
        }

        // return 한 값으로 에러 처리 필요

        endDt = endDt.plusSeconds(1);

        sync.setChgStartDate(endDt);
        syncService.updateChgDate(sync);

        LocalDate now = LocalDate.now();

        if (now.getDayOfYear() == 256) { //9월13일로 설정. 1로 설정시 1월1일
            System.err.println("DataDeleteStart");
            // 3년 전으로 이동하고 자정으로 설정
            LocalDate threeYearsAgo = now.minusYears(3).atStartOfDay().toLocalDate();
            System.err.println("threeYearsAgo==>"+threeYearsAgo);
            // "yyyy-MM-dd" 형식의 문자열로 변환
            String dateToString = threeYearsAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 23:59:59";
            System.err.println("dateToString==>"+dateToString);

            Account account = new Account();
            account.setRetireDate(dateToString);
            try {
                accountService.deleteRetireAccount(account);
                accountService.deleteRetireUseridAuth(account);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("failed");
            }
            System.err.println("success");
        }
    }
}
