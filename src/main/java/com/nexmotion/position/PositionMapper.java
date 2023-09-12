package com.nexmotion.position;

import java.util.List;

import com.nexmotion.organ.Organ;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PositionMapper {

  public List<Position> selectPosition() throws Exception;
  
  public void insertPosition(List<Position> list) throws Exception;
}
