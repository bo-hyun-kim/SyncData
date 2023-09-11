package com.nexmotion.position;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService implements PositionMapper {
  
  @Autowired
  private PositionMapper positionMapper;

  @Override
  public void insertPosition(List<Position> list) throws Exception {
    positionMapper.insertPosition(list);
  }
}
