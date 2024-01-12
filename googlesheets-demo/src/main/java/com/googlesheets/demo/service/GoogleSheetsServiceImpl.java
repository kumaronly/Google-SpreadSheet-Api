package com.googlesheets.demo.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.googlesheets.demo.config.GoogleAuthorizationConfig;
import com.googlesheets.demo.dto.EmpDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetsServiceImpl implements GoogleSheetsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetsServiceImpl.class);

	@Value("${spreadsheet.id}")
	private String spreadsheetId;

	@Autowired
	private GoogleAuthorizationConfig googleAuthorizationConfig;

	@Override
	public void getSpreadsheetValues() throws IOException, GeneralSecurityException {
		Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
		Sheets.Spreadsheets.Values.BatchGet request = sheetsService.spreadsheets().values().batchGet(spreadsheetId);
		request.setRanges(getSpreadSheetRange());
		request.setMajorDimension("ROWS");
		BatchGetValuesResponse response = request.execute();
		List<List<Object>> spreadSheetValues = response.getValueRanges().get(0).getValues();
		List<Object> headers = spreadSheetValues.remove(0);
		for (List<Object> row : spreadSheetValues) {
			LOGGER.info("{}: {}, {}: {}, {}: {}, {}: {}", headers.get(0), row.get(0), headers.get(1), row.get(1),
					headers.get(2), row.get(2), headers.get(3), row.get(3));
		}
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

	@Override
	public void updateSpreadsheetValues(EmpDTO empDTO) throws IOException, GeneralSecurityException {
		Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
		Sheets.Spreadsheets.Values.BatchGet request = sheetsService.spreadsheets().values().batchGet(spreadsheetId);
		request.setRanges(getSpreadSheetRange());
		request.setMajorDimension("ROWS");
		BatchGetValuesResponse response = request.execute();
		List<List<Object>> spreadSheetValues = response.getValueRanges().get(0).getValues();
		List<Object> headers = spreadSheetValues.remove(0);
		String range = null;
		int rowValue=2;
		for (List<Object> row : spreadSheetValues) {
			if(Integer.parseInt(row.get(0).toString())==(empDTO.getEmpId()) 
					&& row.get(1).toString().equalsIgnoreCase(empDTO.getDate())) {
				System.out.println(row.get(0).toString());
				range = "A"+ rowValue;
				System.out.println("range new value" +range);
				ValueRange body = new ValueRange()
						.setValues(Arrays.asList(Arrays.asList(empDTO.getEmpId(), empDTO.getDate(), empDTO.getFirstName(),
								empDTO.getLastName(), empDTO.getEmail(), empDTO.getAge(), empDTO.getPhoneNumber())));
				UpdateValuesResponse result = sheetsService.spreadsheets().values().update(spreadsheetId, range, body)
						.setValueInputOption("RAW").execute();

				System.out.println("Updated " + result.getUpdatedCells() + " cells.");
			}
			rowValue++;
			LOGGER.info("{}: {}, {}: {}, {}: {}, {}: {}", headers.get(0), row.get(0), headers.get(1),
					row.get(1),headers.get(2), row.get(2), headers.get(3), row.get(3), headers.get(4), 
					row.get(4),headers.get(5), row.get(5));
		}
		

	}
}
