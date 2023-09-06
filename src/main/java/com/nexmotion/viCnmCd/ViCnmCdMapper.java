package com.nexmotion.viCnmCd;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ViCnmCdMapper {
  
  public void insertViCnmCd(List<ViCnmCd> list) throws Exception;
}
