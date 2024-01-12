package com.googlesheets.demo.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.googlesheets.demo.dto.EmpDTO;
import com.googlesheets.demo.dto.ErReleaseDto;

public interface GoogleSheetsService {

	public void getSpreadsheetValues() throws IOException, GeneralSecurityException;

	public void updateSpreadsheetValues(EmpDTO empDTO) throws IOException, GeneralSecurityException;

}