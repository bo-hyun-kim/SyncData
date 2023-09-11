package com.nexmotion.position;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PositionMapper {
  
  public void insertPosition(List<Position> list) throws Exception;
}
