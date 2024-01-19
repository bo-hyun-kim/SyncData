package com.nexmotion.position;

import java.sql.SQLException;
import java.util.List;

import com.nexmotion.account.Account;
import com.nexmotion.organ.Organ;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PositionMapper {

  public List<Position> selectPosition() throws SQLException;
  
  public void insertPositionList(List<Position> list) throws SQLException;

  public void insertPosition(Position position) throws SQLException;

  public void updatePosition(Position position) throws SQLException;

  public void deletePosition() throws SQLException;

}
