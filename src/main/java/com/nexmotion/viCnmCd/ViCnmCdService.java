package com.nexmotion.viCnmCd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViCnmCdService implements ViCnmCdMapper {
  
  @Autowired
  private ViCnmCdMapper viCnmCdMapper;

  @Override
  public void insertViCnmCd(List<ViCnmCd> list) throws Exception {
    viCnmCdMapper.insertViCnmCd(list);
  }
}
