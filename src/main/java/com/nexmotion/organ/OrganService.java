package com.nexmotion.organ;

import java.sql.SQLException;
import java.util.List;

import com.nexmotion.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganService implements OrganMapper {

  @Autowired
  private OrganMapper organMapper;

  @Override
  public List<Organ> selectOrgan() throws SQLException {
    return organMapper.selectOrgan();
  }

  @Override
  public void insertOrganList(List<Organ> list) throws SQLException {
    organMapper.insertOrganList(list);
  }

  @Override
  public void updateOrgan(Organ organ) throws SQLException {
    organMapper.updateOrgan(organ);
  }

  @Override
  public void insertOrgan(Organ organ) throws SQLException {
    organMapper.insertOrgan(organ);
  }

  @Override
  public void truncateOragan() throws SQLException {
    organMapper.truncateOragan();
  }
}
