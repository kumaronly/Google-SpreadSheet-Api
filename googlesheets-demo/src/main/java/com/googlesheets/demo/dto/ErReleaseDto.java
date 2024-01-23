package com.googlesheets.demo.dto;

import lombok.Data;

@Data
public class ErReleaseDto {

	private long domainId;
	private String domainName;
	private String codeConfigPNO;
	private String vastId;
	private String managerSignoff;
	private String onshoreDeliveryLead;
	private String vziDeliveryLead;
	private String projectNumberName;
	
}
