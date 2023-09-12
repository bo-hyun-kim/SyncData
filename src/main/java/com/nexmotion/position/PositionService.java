package com.nexmotion.position;

import java.util.List;

import com.nexmotion.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
