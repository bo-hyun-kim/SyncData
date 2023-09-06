package com.nexmotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nexmotion.requester.OrganizationRequester;
import com.nexmotion.requester.UserRequester;

@SpringBootApplication
public class SyncNpaApplication implements CommandLineRunner {

	@Autowired
	private UserRequester userRequester;
	
	@Autowired
	private OrganizationRequester organizationRequester;
	
	public static void main(String[] args) {
		SpringApplication.run(SyncNpaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if (!organizationRequester.run()) {
			System.err.println("조직정보 동기화 실패!");
			return;
		}
		
		if (!userRequester.run()) {
			System.err.println("사용자정보 동기화 실패!");
			return;
		}
	}
}
