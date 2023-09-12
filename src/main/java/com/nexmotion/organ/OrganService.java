package com.nexmotion.organ;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganService implements OrganMapper {

  @Autowired
  private OrganMapper organMapper;

  @Override
  public List<Organ> selectOrgan() throws Exception {
    return organMapper.selectOrgan();
  }

  @Override
  public void insertOrgan(List<Organ> list) throws Exception {
    organMapper.insertOrgan(list);
  }
  
}
