package com.nexmotion.requester;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestDTO {
	
	private String qryPups;
	private int reqCl;
	private int page;
	private LocalDateTime chgStartDttm;
	private LocalDateTime chgEndDttm;

}
