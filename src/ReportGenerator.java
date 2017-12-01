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

/**
 * @purpose Report Generator class creates the author's custom royalty report .xlsx spreadsheet
 * @author Ian Moreno
 * @date 12/1/2017
 */

public class ReportGenerator {

	/**
	 * @purpose public static method takes in the authorsAndASINs map and the set of entered reports and outputs a custom report
	 * @param authorsAndASINs Mapping from each author and their corresponding ASINs
	 * @param reports set of the Amazon reports to parse
	 */
	public static void createReport(Map<String, ArrayList> authorsAndASINs, HashSet<File> reports) {
		// set of the authors enterd
		Set authors = authorsAndASINs.keySet();
		// initialize the bookData ArrayList
		ArrayList<Book> bookData = new ArrayList<>();
		
		// for each report spreadhseet entered
		for (File report : reports) {
			// add the List of Book's in the report to the bookData Arraylist
			bookData.addAll((ArrayList)RetailerRawData.getData(report.getPath()));
		}
		
		// declare and initialize variables outside the loop
		String authorName = "";
		String reportName = "";

		// for each author in the authorsAndASINs map
		for (Object author : authors) {
			// save the author's name
    			authorName = author.toString();
    			// save the report name
    			reportName = authorName + "'s Amazon Report";
    			// build the amazonReport
    			AuthorReport amazonReport = new AuthorReport(reportName, bookData, authorsAndASINs.get(authorName));
    			// print data to console
    			System.out.println(authorName);
    			System.out.println(amazonReport);

    			// write the new report spreadsheet for the corresponding author
    			writeXLSXSpreadSheet(reportName, amazonReport);
    		}
	}
	
	/**
	 * @purpose private static method takes in the report name and amazon report and creates and outputs
	 * 			author's custom report spreadsheet
	 * @param reportName String variable representing the name of the author's report
	 * @param amazonReport AuthorReport containing all the bookData belonging to the author
	 */
	private static void writeXLSXSpreadSheet(String reportName, AuthorReport amazonReport) {
		String reportFileName = (reportName + ".xlsx"); //name of excel file
		String sheetName = "Amazon Royalties"; //name of sheet

		// declare new workbook object
		XSSFWorkbook wb = new XSSFWorkbook();
		// declare new Sheet object
		XSSFSheet sheet = wb.createSheet(sheetName) ;
		// declare new Row object
		XSSFRow row = null;
		// declare new Cell object
		XSSFCell cell = null;
		// delcaire new FilOutputStream object
		FileOutputStream fileOut = null;

		// save an instance of the author's bookList
		ArrayList<Book> bookList = (ArrayList)amazonReport.getAuthorReport();

		// set iteration boundaries
		int numberOfRows = bookList.size() + 3; 
		int numberOfColumns = 0;
		// delcaire and initialize royaltyTotal variable
		double royaltyTotal = 0.0;
		// declare the bookDataMap outside the loop
		Map<String, String> bookDataMap;

		// initialize header row in case bookList is empty
		ArrayList<String> headerRow = new ArrayList<>(); 

		// add column headers to headerRow
		headerRow.add("Title");
		headerRow.add("Author");
		headerRow.add("ASIN"); 
		headerRow.add("Marketplace");
		headerRow.add("Units Sold");
		headerRow.add("Units Refunded"); 
		headerRow.add("Net Units Sold");
		headerRow.add("Royalty Type");
		headerRow.add("Transaction Type");
		headerRow.add("Currency");
		headerRow.add("Avg. List Price without tax");
		headerRow.add("Avg. Offer Price without tax");
		headerRow.add("Avg. File Size (MB)");
		headerRow.add("Avg. Delivery Cost");
		headerRow.add("Royalty");
		
		numberOfColumns = headerRow.size();
		
		// create and insert title row
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue(reportName);
		
		// insert empty row
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("");

		// create header row
		row = sheet.createRow(2);
		for (int c = 0; c < numberOfColumns; c++) {
			cell = row.createCell(c);
			cell.setCellValue(headerRow.get(c));
			
			if ((c + 1) == numberOfColumns) {
				cell = row.createCell(c+1);
				cell.setCellValue("Royalty (USD)");
			}
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
				
				// if last row then calculate Royalty in (USD)
				if ((c + 1) == numberOfColumns) {
					String currencyType = bookDataMap.get("Currency");
					double royalty = Double.parseDouble(bookDataMap.get("Royalty"));
					double convertedRoyalty = CurrencyConverter.getConversion(currencyType, royalty);
					cell = row.createCell(c+1);
					cell.setCellValue(convertedRoyalty);
					royaltyTotal += convertedRoyalty;
				}
			}
			// add the total royalty at end of report
			if ((r+1) == numberOfRows) {
				row = sheet.createRow(r+1);

				cell = row.createCell(0);
				cell.setCellValue("Royalty Total");

				cell = row.createCell(numberOfColumns);
				cell.setCellValue(royaltyTotal);
			}
		}

		try { // create new FileOutputStream object
			fileOut = new FileOutputStream(new File(reportFileName));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		} finally {
			try {
				//write this workbook to an Outputstream.
				wb.write(fileOut);
				fileOut.flush(); // flush the stream buffer
				fileOut.close();	 // close the FileOutputStream
			} catch (IOException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}
	}
}
