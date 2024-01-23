package com.googlesheets.demo.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.googlesheets.demo.config.GoogleAuthorizationConfig;
import com.googlesheets.demo.dto.ErReleaseDto;
import com.googlesheets.demo.dto.GoogleSheetResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service(value = "erreleaseimpl")
public class ErReleaseSheetImpl implements ErReleaseSheet {

	@Value("${spreadsheet.id}")
	private String spreadsheetId;

	@Autowired
	private GoogleAuthorizationConfig googleAuthorizationConfig;

	@Override
	public List<ErReleaseDto> getReleaseTrackerSheet() throws IOException, GeneralSecurityException {
		Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
		Sheets.Spreadsheets.Values.BatchGet request = sheetsService.spreadsheets().values().batchGet(spreadsheetId);
		request.setRanges(getSpreadSheetRange());
		request.setMajorDimension("ROWS");
		BatchGetValuesResponse response = request.execute();
		List<List<Object>> spreadSheetValues = response.getValueRanges().get(0).getValues();
		List<Object> headers = spreadSheetValues.remove(0);
		List<ErReleaseDto> erReleaseDtoList = new ArrayList<>();
		for (List<Object> row : spreadSheetValues) {
			ErReleaseDto releaseDto = new ErReleaseDto();
			releaseDto.setDomainId(Long.parseLong(row.get(0).toString()));
			releaseDto.setDomainName(row.get(1).toString());
			releaseDto.setCodeConfigPNO(row.get(2).toString());
			releaseDto.setVastId(row.get(3).toString());
			releaseDto.setManagerSignoff(row.get(4).toString());
			releaseDto.setOnshoreDeliveryLead(row.get(5).toString());
			releaseDto.setVziDeliveryLead(row.get(6).toString());
			releaseDto.setProjectNumberName(row.get(7).toString());
			erReleaseDtoList.add(releaseDto);
			log.info("{}: {}, {}: {}, {}: {}, {}: {}, {}: {}, {}: {}, {}: {}", headers.get(0), row.get(0),
					headers.get(1), row.get(1), headers.get(2), row.get(2), headers.get(3), row.get(3), headers.get(4),
					row.get(4), headers.get(5), row.get(5), headers.get(6), row.get(6), headers.get(7), row.get(7));
		}

		return erReleaseDtoList;
	}

	@Override
	public GoogleSheetResponse updateReleaseTrackerSheet(long domainId, ErReleaseDto releaseDto)
			throws IOException, GeneralSecurityException {
		GoogleSheetResponse googleSheetResponse = new GoogleSheetResponse();
		List<ErReleaseDto> erReleaseDtoList = new ArrayList<>();
		Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
		Sheets.Spreadsheets.Values.BatchGet request = sheetsService.spreadsheets().values().batchGet(spreadsheetId);
		request.setRanges(getSpreadSheetRange());
		request.setMajorDimension("ROWS");
		BatchGetValuesResponse response = request.execute();
		List<List<Object>> spreadSheetValues = response.getValueRanges().get(0).getValues();
		List<Object> headers = spreadSheetValues.remove(0);
		String message = null;
		String range = null;
		int rowValue = 2;
		for (List<Object> row : spreadSheetValues) {
			if (Long.parseLong(row.get(0).toString()) == domainId) {

				range = "A" + rowValue;
				log.info("Row index : " + range);
				ValueRange body = new ValueRange().setValues(Arrays.asList(Arrays.asList(domainId,
						releaseDto.getDomainName() != null && !releaseDto.getDomainName().isEmpty()
								? releaseDto.getDomainName()
								: "NA",
						releaseDto.getCodeConfigPNO() != null && !releaseDto.getCodeConfigPNO().isEmpty()
								? releaseDto.getCodeConfigPNO()
								: "NA",
						releaseDto.getVastId() != null && !releaseDto.getVastId().isEmpty() ? releaseDto.getVastId()
								: "NA",
						releaseDto.getManagerSignoff() != null && !releaseDto.getManagerSignoff().isEmpty()
								? releaseDto.getManagerSignoff()
								: "NA",
						releaseDto.getOnshoreDeliveryLead() != null && !releaseDto.getOnshoreDeliveryLead().isEmpty()
								? releaseDto.getOnshoreDeliveryLead()
								: "NA",
						releaseDto.getVziDeliveryLead() != null && !releaseDto.getVziDeliveryLead().isEmpty()
								? releaseDto.getVziDeliveryLead()
								: "NA",
						releaseDto.getProjectNumberName() != null && !releaseDto.getProjectNumberName().isEmpty()
								? releaseDto.getProjectNumberName()
								: "NA")));

				UpdateValuesResponse result = sheetsService.spreadsheets().values().update(spreadsheetId, range, body)
						.setValueInputOption("RAW").execute();

				ErReleaseDto erReleaseDto = new ErReleaseDto();
				erReleaseDto.setDomainId(domainId);
				erReleaseDto.setDomainName(releaseDto.getDomainName() != null ? releaseDto.getDomainName() : "NA");
				erReleaseDto
						.setCodeConfigPNO(releaseDto.getCodeConfigPNO() != null ? releaseDto.getCodeConfigPNO() : "NA");
				erReleaseDto.setVastId(releaseDto.getVastId() != null ? releaseDto.getVastId() : "NA");
				erReleaseDto.setManagerSignoff(
						releaseDto.getManagerSignoff() != null ? releaseDto.getManagerSignoff() : "NA");
				erReleaseDto.setOnshoreDeliveryLead(
						releaseDto.getOnshoreDeliveryLead() != null ? releaseDto.getOnshoreDeliveryLead() : "NA");
				erReleaseDto.setVziDeliveryLead(
						releaseDto.getVziDeliveryLead() != null ? releaseDto.getVziDeliveryLead() : "NA");
				erReleaseDto.setProjectNumberName(
						releaseDto.getProjectNumberName() != null ? releaseDto.getProjectNumberName() : "NA");
				erReleaseDtoList.add(erReleaseDto);

				log.info("{}: {}, {}: {}, {}: {}, {}: {}, {}: {}, {}: {}, {}: {}", headers.get(0), row.get(0),
						headers.get(1), row.get(1), headers.get(2), row.get(2), headers.get(3), row.get(3),
						headers.get(4), row.get(4), headers.get(5), row.get(5), headers.get(6), row.get(6),
						headers.get(7), row.get(7));

				message = "Updated Successfully";
				googleSheetResponse.setErReleaseDtoList(erReleaseDtoList);
			}
			rowValue++;
		}
		if (message != null) {
			googleSheetResponse.setMessage(message);
		} else {
			googleSheetResponse.setMessage("The data is not present for the given input. Insert valid input ");
		}
		return googleSheetResponse;
	}

	private List<String> getSpreadSheetRange() throws IOException, GeneralSecurityException {
		Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
		Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spreadsheetId);
		Spreadsheet spreadsheet = request.execute();
		Sheet sheet = spreadsheet.getSheets().get(0);
		int row = sheet.getProperties().getGridProperties().getRowCount();
		int col = sheet.getProperties().getGridProperties().getColumnCount();
		return Collections.singletonList("R1C1:R".concat(String.valueOf(row)).concat("C").concat(String.valueOf(col)));
	}
}
