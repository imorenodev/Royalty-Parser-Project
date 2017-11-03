import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportGenerator {

	public static void createReport(Map<String, ArrayList> authorsAndASINs, HashSet<File> reports) {
		Set authors = authorsAndASINs.keySet();
		ArrayList<File> reportFiles = new ArrayList<>();
		
		for (File report : reports) {
			reportFiles.add(report);
		}
		
		ArrayList<Book> bookData = (ArrayList)RetailerRawData.getData(reportFiles.get(0).getPath());
		
		String authorName = "";
		String reportName = "";

		for (Object author : authors) {
    			authorName = author.toString();
    			reportName = authorName + "'s Amazon Report";
    			AuthorReport amazonReport = new AuthorReport(reportName, bookData, authorsAndASINs.get(authorName));
    			System.out.println(authorName);
    			System.out.println(amazonReport);

    			writeXLSXSpreadSheet(reportName, amazonReport);
    		}
	}
	
	private static void writeXLSXSpreadSheet(String reportName, AuthorReport amazonReport) {
		String reportFileName = (reportName + ".xlsx"); //name of excel file
		String sheetName = "Amazon Royalties"; //name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;
		XSSFRow row = null;
		XSSFCell cell = null;
		FileOutputStream fileOut = null;

		// build sheet
		ArrayList<Book> bookList = (ArrayList)amazonReport.getAuthorReport();

		int numberOfRows = bookList.size() + 3; 
		int numberOfColumns = 0;
		Map<String, String> bookDataMap;

		// initialize header row in case bookList is empty
		ArrayList<String> headerRow = new ArrayList<>(); 

		if (bookList.get(0) != null) { // if there's at least one book entry then save it's keys as the column headers
			headerRow.addAll(bookList.get(0).getBookDataMap().keySet());
		}
		
		numberOfColumns = headerRow.size();
		
		// create title
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue(reportName);
		
		// empty row
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("");

		// create header row
		row = sheet.createRow(2);
		for (int c = 0; c < numberOfColumns; c++) {
			cell = row.createCell(c);
			cell.setCellValue(headerRow.get(c));
		}
		
		// iterating r+3 number of rows
		for (int r = 3; r < numberOfRows; r++)
		{
			row = sheet.createRow(r);
			bookDataMap = bookList.get(r - 3).getBookDataMap();

			//iterating c number of columns
			for (int c = 0;c < numberOfColumns; c++ )
			{
				cell = row.createCell(c);
	
				cell.setCellValue(bookDataMap.get(headerRow.get(c)));
			}
		}

		try {
			fileOut = new FileOutputStream(reportFileName);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		} finally {
			try {
				//write this workbook to an Outputstream.
				wb.write(fileOut);
				fileOut.flush();
				fileOut.close();	
			} catch (IOException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}
	}
}
