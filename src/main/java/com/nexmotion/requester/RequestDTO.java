package com.nexmotion.requester;

import lombok.Data;

@Data
public class RequestDTO {
	
	private String qryPups;
	private int reqCl;
	private int page;
	private String chgStartDttm;
	private String chgEndDttm;

}
