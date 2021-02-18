package com.ERP.Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil 
{
	Workbook wb;
	
	//Constructor for reading excel path	
	public ExcelFileUtil(String excelpath) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		FileInputStream fi=new FileInputStream(excelpath);
		wb=WorkbookFactory.create(fi);
	}
	
	//Method to count no of rows in a sheet
	public int rowCount(String sheetname)
	{
		return wb.getSheet(sheetname).getLastRowNum();
	}
	
	//Method to count no of columns
	public int colCount(String sheetname)
	{
		return wb.getSheet(sheetname).getRow(0).getLastCellNum();
	}
	
	//Method to read cell data from sheet
	@SuppressWarnings("deprecation")
	public String getCellData(String sheetname,int row,int column)
	{
		String data=" ";
		//to check if cell data is int type
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
		{
			//storing numaric type cell
			int celldata=(int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			//typecasting celldata into String type
			data=String.valueOf(celldata);
		}
		else
		{
			data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}
	//Method to set results in new workbook
	@SuppressWarnings("deprecation")
	public void setcelldata(String sheetname,int row,int column,String status,String writefile) throws IOException
	{
		//get sheet
		Sheet ws=wb.getSheet(sheetname);
		//get row
		Row rownum=ws.getRow(row);
		//create cell
		Cell cell=rownum.createCell(column);
		//Cell cell=wb.getSheet(sheetname).getRow(row).createCell(column);
		//write cell value
		cell.setCellValue(status);
		//for setting colors to specific status
		if(status.equalsIgnoreCase("Pass"))
		{
			//create cell style
			CellStyle style=wb.createCellStyle();
			//create font
			Font font=wb.createFont();
			//Apply color to font
			font.setColor(IndexedColors.GREEN.getIndex());
			//Apply bold to text
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rownum.getCell(column).setCellStyle(style);
		}
		if(status.equalsIgnoreCase("Fail"))
		{
			//create cell style
			CellStyle style=wb.createCellStyle();
			//create font
			Font font=wb.createFont();
			//Apply color to font
			font.setColor(IndexedColors.RED.getIndex());
			//Apply bold to text
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rownum.getCell(column).setCellStyle(style);
		}
		if(status.equalsIgnoreCase("Blocked"))
		{
			//create cell style
			CellStyle style=wb.createCellStyle();
			//create font
			Font font=wb.createFont();
			//Apply color to font
			font.setColor(IndexedColors.BLUE.getIndex());
			//Apply bold to text
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rownum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(writefile);
		wb.write(fo);
		
	}
	

}
