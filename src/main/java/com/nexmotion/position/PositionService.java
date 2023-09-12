package com.nexmotion.position;

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
  @Transactional
  public void insertPositionList(List<Position> list) throws Exception {
    positionMapper.insertPositionList(list);
  }

  @Override
  @Transactional
  public void updatePosition(Position position) throws Exception {
    positionMapper.updatePosition(position);
  }

  @Override
  @Transactional
  public void insertPosition(Position position) throws Exception {
    positionMapper.insertPosition(position);
  }
}
