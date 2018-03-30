package de.rwthaachen.cosa.frontend.excel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelBuilder extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> arg0, HSSFWorkbook arg1, HttpServletRequest arg2,
			HttpServletResponse arg3) throws Exception {
		// TODO Auto-generated method stub
		
		arg3.setHeader("Content-Disposition", "attachment; filename=\"codesmell-report.xls\""); 
		@SuppressWarnings("unchecked")
		List<DetectedCodesmell> list = (List<DetectedCodesmell>) arg0.get("listdata");
		
		HSSFSheet sheet = arg1.createSheet("Detected Code Smell");
		sheet.setDefaultColumnWidth(20);
		
		HSSFCellStyle style = arg1.createCellStyle();
		HSSFFont font = arg1.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(true);
		style.setFont(font);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Class Name");
		header.getCell(0).setCellStyle(style);
		
		header.createCell(1).setCellValue("Programming Language");
		header.getCell(1).setCellStyle(style);
		
		header.createCell(2).setCellValue("Codesmell Type");
		header.getCell(2).setCellStyle(style);
		
		header.createCell(3).setCellValue("Activity");
		header.getCell(3).setCellStyle(style);
		
		header.createCell(4).setCellValue("Harmfulness Level");
		header.getCell(4).setCellStyle(style);
		
		header.createCell(5).setCellValue("Classification Type");
		header.getCell(5).setCellStyle(style);
		
		//value
		HSSFCellStyle style1 = arg1.createCellStyle();
		HSSFFont font1 = arg1.createFont();
		font1.setFontName(HSSFFont.FONT_ARIAL);
		font1.setBold(false);
		style1.setFont(font1);
		style1.setBorderLeft(BorderStyle.MEDIUM);
		style1.setBorderTop(BorderStyle.THIN);
		style1.setBorderRight(BorderStyle.MEDIUM);
		style1.setBorderBottom(BorderStyle.THIN);
		
		int row = 1;
		for(DetectedCodesmell dc : list){
			HSSFRow hrow = sheet.createRow(row++);
			
			hrow.createCell(0).setCellValue(dc.getClassName());
			hrow.getCell(0).setCellStyle(style1);
			
			hrow.createCell(1).setCellValue(dc.getProgrammingLanguage());
			hrow.getCell(1).setCellStyle(style1);
			
			hrow.createCell(2).setCellValue(dc.getCodesmellType());
			hrow.getCell(2).setCellStyle(style1);
			
			hrow.createCell(3).setCellValue(dc.getActivityType());
			hrow.getCell(3).setCellStyle(style1);
			
			hrow.createCell(4).setCellValue(BigDecimal.valueOf(dc.getHarmfulnessLevel()).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
			hrow.getCell(4).setCellStyle(style1);
			
			hrow.createCell(5).setCellValue(dc.getClassificationType());
			hrow.getCell(5).setCellStyle(style1);
		}
		
		HSSFCellStyle style2 = arg1.createCellStyle();
		style2.setFont(font1);
		style2.setBorderLeft(BorderStyle.MEDIUM);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.MEDIUM);
		style2.setBorderBottom(BorderStyle.MEDIUM);
		HSSFRow hrow = sheet.getRow(row -1);
		hrow.getCell(0).setCellStyle(style2);
		hrow.getCell(1).setCellStyle(style2);
		hrow.getCell(2).setCellStyle(style2);
		hrow.getCell(3).setCellStyle(style2);
		hrow.getCell(4).setCellStyle(style2);
		hrow.getCell(5).setCellStyle(style2);
	}

}
