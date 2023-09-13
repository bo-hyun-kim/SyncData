package com.nexmotion.sync;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface SyncMapper {

    public List<SyncVO> getChgDate() throws SQLException;

    public void updateChgDate(SyncVO sync) throws SQLException;

}
