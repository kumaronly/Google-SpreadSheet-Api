package com.googlesheets.demo.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.googlesheets.demo.dto.ErReleaseDto;
import com.googlesheets.demo.dto.GoogleSheetResponse;

public interface ErReleaseSheet {

	public List<ErReleaseDto> getReleaseTrackerSheet() throws IOException, GeneralSecurityException;

	public GoogleSheetResponse updateReleaseTrackerSheet(long domainId, ErReleaseDto releaseDto) throws IOException, GeneralSecurityException;

}
