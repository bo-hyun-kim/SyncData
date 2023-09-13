package com.nexmotion.organ;

import java.sql.SQLException;
import java.util.List;

import com.nexmotion.account.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrganMapper {

  public List<Organ> selectOrgan() throws Exception;

  public void insertOrganList(List<Organ> list) throws Exception;

  public void insertOrgan(Organ organ) throws Exception;

  public void updateOrgan(Organ organ) throws Exception;

  public void truncateOragan() throws SQLException;

}
