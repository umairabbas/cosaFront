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

public class ExcelBuilderAllVersions extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> arg0, HSSFWorkbook arg1, HttpServletRequest arg2,
			HttpServletResponse arg3) throws Exception {
		// TODO Auto-generated method stub
		arg3.setHeader("Content-Disposition", "attachment; filename=\"allversions-codesmell-report.xls\""); 
		@SuppressWarnings("unchecked")
		List<DetectedCodesmellAllVersions> list = (List<DetectedCodesmellAllVersions>) arg0.get("listdata");
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
		header.createCell(0).setCellValue("Version");
		header.getCell(0).setCellStyle(style);
		
		header.createCell(1).setCellValue("Class Name");
		header.getCell(1).setCellStyle(style);
		
		header.createCell(2).setCellValue("Programming Language");
		header.getCell(2).setCellStyle(style);
		
		header.createCell(3).setCellValue("Codesmell Type");
		header.getCell(3).setCellStyle(style);
		
		header.createCell(4).setCellValue("Activity");
		header.getCell(4).setCellStyle(style);
		
		header.createCell(5).setCellValue("Impact");
		header.getCell(5).setCellStyle(style);
		
		header.createCell(6).setCellValue("Activeness");
		header.getCell(6).setCellStyle(style);
		
		header.createCell(7).setCellValue("ACT");
		header.getCell(7).setCellStyle(style);
		
		header.createCell(8).setCellValue("IMP");
		header.getCell(8).setCellStyle(style);
		
		header.createCell(9).setCellValue("Harmfulness");
		header.getCell(9).setCellStyle(style);
		
		header.createCell(10).setCellValue("Harmfulness Level");
		header.getCell(10).setCellStyle(style);
		
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
		
		System.out.println("here");
		System.out.println(list.size());
		int row = 1;
		for(DetectedCodesmellAllVersions dcav : list){
			HSSFRow hrow = sheet.createRow(row++);
			
			hrow.createCell(0).setCellValue(dcav.getVersion());
			hrow.getCell(0).setCellStyle(style1);
			
			hrow.createCell(1).setCellValue(dcav.getClassName());
			hrow.getCell(1).setCellStyle(style1);
			
			hrow.createCell(2).setCellValue(dcav.getProgrammingLanguage());
			hrow.getCell(2).setCellStyle(style1);
			
			hrow.createCell(3).setCellValue(dcav.getCodesmellType());
			hrow.getCell(3).setCellStyle(style1);
			
			hrow.createCell(4).setCellValue(dcav.getActivityType());
			hrow.getCell(4).setCellStyle(style1);
			
			hrow.createCell(5).setCellValue(BigDecimal.valueOf(dcav.getImpact()).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
			hrow.getCell(5).setCellStyle(style1);
			
			hrow.createCell(6).setCellValue(BigDecimal.valueOf(dcav.getActiveness()).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
			hrow.getCell(6).setCellStyle(style1);
			
			hrow.createCell(7).setCellValue(BigDecimal.valueOf(dcav.getACT()).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
			hrow.getCell(7).setCellStyle(style1);
			
			hrow.createCell(8).setCellValue(BigDecimal.valueOf(dcav.getIMP()).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
			hrow.getCell(8).setCellStyle(style1);
			
			hrow.createCell(9).setCellValue(BigDecimal.valueOf(dcav.getHarmfulness()).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
			hrow.getCell(9).setCellStyle(style1);
			
			hrow.createCell(10).setCellValue(dcav.getClassificationType());
			hrow.getCell(10).setCellStyle(style1);
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
		System.out.println("final");
	}

}
