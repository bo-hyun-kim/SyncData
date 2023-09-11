package com.nexmotion.sync;

import com.nexmotion.requester.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SyncData {

    private final Logger LOGGER = LoggerFactory.getLogger(SyncData.class);

    @Autowired
    private SyncService syncService;

    @Autowired
    private UserRequester userRequester;

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


    @Transactional
    public void sync() throws Exception {

        SyncVO sync = new SyncVO();
        List<SyncVO> dateInfo = new ArrayList<>();

        dateInfo = syncService.getChgDate();

        LocalDateTime startDt = dateInfo.get(0).getChgstartdate();
        System.err.println("startDt===>"+startDt);

        if (startDt == null) {
            LOGGER.info("truncate all tables");
            //실제로 지금 계정 테이블이랑 조직 테이블 샘플 데이터가 truncate 되면 안되니까 일단 주석처리 해놓음
//            syncService.truncateAccount();
//            syncService.truncateOraganization();
            startDt = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        }

        LocalDateTime endDt = LocalDateTime.now();
        sync.setChgenddate(endDt);

        boolean userResult = userRequester.run(startDt, endDt, 1);
        boolean userComplainResult = userComplainRequester.run(startDt, endDt, 1);
        boolean organResult = organizationRequester.run(startDt, endDt, 2);
        boolean organRecordResult = organRecordRequester.run(startDt, endDt, 2);
        boolean postionResult = positionRequester.run(startDt, endDt, 3);
        boolean jobGradeResult = jobGradeRequester.run(startDt, endDt, 3);
        boolean commonCodeResult = commonCodeRequester.run(startDt, endDt, 3);

        // return 한 값으로 에러 처리 필요

        endDt = endDt.plusSeconds(1);

        sync.setChgstartdate(endDt);
        syncService.updateChgDate(sync);
    }
}
