package com.nexmotion.sync;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface SyncMapper {

    public List<Sync> getChgDate() throws SQLException;

    public void updateChgDate(Sync sync) throws SQLException;

    public void truncateAccount() throws SQLException;

    public void truncateOraganization() throws SQLException;
}
