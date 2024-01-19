package com.nexmotion.organ;

import java.sql.SQLException;
import java.util.List;

import com.nexmotion.account.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrganMapper {

  public List<Organ> selectOrgan() throws SQLException;

  public void insertOrganList(List<Organ> list) throws SQLException;

  public void insertOrgan(Organ organ) throws SQLException;

  public void updateOrgan(Organ organ) throws SQLException;

  public void deleteOrgan() throws SQLException;

}
