package com.nexmotion.organ;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrganMapper {

  public void insertOrgan(List<Organ> list) throws Exception;
  
}
