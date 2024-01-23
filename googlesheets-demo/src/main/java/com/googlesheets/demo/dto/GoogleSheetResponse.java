package com.googlesheets.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class GoogleSheetResponse {
	private String message;
	private List<ErReleaseDto> erReleaseDtoList; 

}
