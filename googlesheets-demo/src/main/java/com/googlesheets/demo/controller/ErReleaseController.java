package com.googlesheets.demo.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.googlesheets.demo.dto.ErReleaseDto;
import com.googlesheets.demo.dto.GoogleSheetResponse;
import com.googlesheets.demo.service.ErReleaseSheet;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value ="/google/sheet/data")
public class ErReleaseController {

	@Autowired
	@Qualifier(value ="erreleaseimpl")
	private ErReleaseSheet erReleaseSheet;
	
	@GetMapping
	public ResponseEntity<List<ErReleaseDto>> getReleaseTrackerSheet() throws IOException, GeneralSecurityException{
		log.info("ErReleaseController | getReleaseTrackerSheet method is calling");
		List<ErReleaseDto> dtos = erReleaseSheet.getReleaseTrackerSheet();
		return new ResponseEntity<List<ErReleaseDto>>(dtos, HttpStatus.OK);
	}
	
	@PutMapping("/update/{domainId}")
	public ResponseEntity<GoogleSheetResponse> updateReleaseTrackerSheet(@PathVariable long domainId, 
			@RequestBody ErReleaseDto erReleaseDto) throws IOException, GeneralSecurityException{
		log.info("ErReleaseController | updateReleaseTrackerSheet method is calling");
		
		 GoogleSheetResponse updateReleaseTrackerSheet = erReleaseSheet.updateReleaseTrackerSheet(domainId, erReleaseDto);
		return new ResponseEntity<GoogleSheetResponse>(updateReleaseTrackerSheet,HttpStatus.OK);
	}
}
