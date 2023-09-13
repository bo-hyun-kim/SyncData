package com.nexmotion.position;

import java.sql.SQLException;
import java.util.List;

import com.nexmotion.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionService implements PositionMapper {
  
  @Autowired
  private PositionMapper positionMapper;

  @Override
  public List<Position> selectPosition() throws Exception {
    return positionMapper.selectPosition();
  }

  @Override
  public void insertPositionList(List<Position> list) throws Exception {
    positionMapper.insertPositionList(list);
  }

  @Override
  public void updatePosition(Position position) throws Exception {
    positionMapper.updatePosition(position);
  }

  @Override
  public void insertPosition(Position position) throws Exception {
    positionMapper.insertPosition(position);
  }

  @Override
  public void truncatePosition() throws SQLException {
    positionMapper.truncatePosition();
  }
}
