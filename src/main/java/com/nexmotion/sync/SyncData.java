package com.nexmotion.sync;

import com.nexmotion.deletedata.DeleteDataService;
import com.nexmotion.requester.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.nexmotion.deletedata.DeleteData;
import com.nexmotion.deletedata.DeleteDataService;

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

    @Autowired
    private DeleteDataService deleteDataService;

    @Transactional(rollbackFor = Exception.class)
    public void sync() throws Exception {

        SyncVO sync = new SyncVO();
        List<SyncVO> dateInfo = new ArrayList<>();

        dateInfo = syncService.getChgDate();

        LocalDateTime startDt = dateInfo.get(0).getChgstartdate();

        if (startDt == null) {
            logger.info("truncate all tables");
            //실제로 지금 계정 테이블이랑 조직 테이블 샘플 데이터가 truncate 되면 안되니까 일단 주석처리 해놓음
//            syncService.truncateAccount();
//            syncService.truncateOraganization();
            startDt = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        }

        LocalDateTime endDt = LocalDateTime.now();
        sync.setChgenddate(endDt);

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

        sync.setChgstartdate(endDt);
        syncService.updateChgDate(sync);

        LocalDate now = LocalDate.now();

        if (now.getDayOfYear() == 255) { //9월12일로 설정. 1로 설정시 1월1일
            System.err.println("DataDeleteStart");
            //System.err.println("minusyears: " + now.minusYears(3));
            //now.minusYears(3);
            String dateToString = now.minusYears(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            DeleteData deletedata = new DeleteData();
            deletedata.setThresholdDate(dateToString);
            try {
                deleteDataService.deletedata(deletedata);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("failed");
            }
            System.err.println("success");
        }
    }
}
