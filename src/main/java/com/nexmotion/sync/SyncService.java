package com.nexmotion.sync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class SyncService implements SyncMapper{

    @Autowired
    SyncMapper syncMapper;

    @Override
    public List<Sync> getChgDate() throws SQLException {
        return syncMapper.getChgDate();
    }

    @Override
    public void truncateAccount() throws SQLException {
        syncMapper.truncateAccount();
    }

    @Override
    public void truncateOraganization() throws SQLException {
        syncMapper.truncateOraganization();
    }

}
