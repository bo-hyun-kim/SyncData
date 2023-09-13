package com.nexmotion.account;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Account {
  private String userid;  
  private String userno; 
  private String username;
  private String gvofcode;  
  private String oposcode;
  private String cposcode;
  private String userstat;
  private String chgdate;
  private String retireDate;
}
