package com.nexmotion.organ;

import java.util.List;

import com.nexmotion.account.Account;
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
  public void insertOrganList(List<Organ> list) throws Exception {
    organMapper.insertOrganList(list);
  }

  @Override
  public void updateOrgan(Organ organ) throws Exception {
    organMapper.updateOrgan(organ);
  }

  @Override
  public void insertOrgan(Organ organ) throws Exception {
    organMapper.insertOrgan(organ);
  }

}
