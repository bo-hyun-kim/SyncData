package com.nexmotion.sync;

import com.nexmotion.requester.OrganizationRequester;
import com.nexmotion.requester.UserRequester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SyncData {

    private final Logger LOGGER = LoggerFactory.getLogger(SyncData.class);

    @Autowired
    SyncService syncService;

    @Autowired
    private UserRequester userRequester;

    @Autowired
    private OrganizationRequester organizationRequester;

    @Transactional
    public void sync() throws Exception {

        List<Sync> dateInfo = new ArrayList<>();
        dateInfo = syncService.getChgDate();

        LocalDateTime startDt = dateInfo.get(0).getChgstartdate();

        if (startDt == null) {
            LOGGER.info("truncate all tables");
            syncService.truncateAccount();
            syncService.truncateOraganization();
            startDt = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        }

        LocalDateTime endDt = LocalDateTime.now();

        organizationSync(startDt, endDt);
        userSync(startDt, endDt);

        endDt += 1초;
        updateDate(endDt);
    }
}
