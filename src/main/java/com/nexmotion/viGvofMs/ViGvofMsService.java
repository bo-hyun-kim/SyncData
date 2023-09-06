package com.nexmotion.viGvofMs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViGvofMsService implements ViGvofMsMapper {

  @Autowired
  private ViGvofMsMapper viGvofMsMapper;

  @Override
  public void insertViGvofMs(List<ViGvofMs> list) throws Exception {
    viGvofMsMapper.insertViGvofMs(list);
  }
  
}
