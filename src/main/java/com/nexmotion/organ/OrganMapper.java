package com.nexmotion.organ;

import java.util.List;

import com.nexmotion.account.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrganMapper {

  public List<Organ> selectOrgan() throws Exception;

  public void insertOrgan(List<Organ> list) throws Exception;
  
}
