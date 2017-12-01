import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @purpose	RetailerRawData class takes a given filepath to a valid, local Amazon royalty spreadsheet and returns
 * 			a list of Book objects containing each book entry (spreadsheet row) and a map of its data items (spreadsheet columns)
 * @author Ian Moreno 
 * @date 12/1/2017
 */

public class RetailerRawData {
	// list of Book objects
	private static List<Book> bookList = new ArrayList<>();

	/**
	 * @purpose public static method takes in a filepath pointing to an Amazon spreadsheet and parses the data
	 * @param filePath String variable representing the filepath to the Amazon spreadsheet
	 * @return List of type Book holding all the individual Book objects
	 */
	public static List<Book> getData(String filePath) {
		InputStream input = null;
		try {
			// assign the input stream to a new FileInputStream passing in the filepath
			input = new FileInputStream(filePath);
			// create a workbook object to operate on the spreadsheet
			Workbook wb = WorkbookFactory.create(input);

			// if no exceptions are thrown then parse the data in the spreadsheet
			getAmazonData(wb.getSheetAt(0));

			// catch exceptions
		} catch (InvalidFormatException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		} finally {
			try { // close the InputFileStream no matter what
				input.close();
			} catch (IOException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}
		return bookList;
	}	

	/**
	 * @purpose	private static method takes a sheet object and parses the row and column data
	 * @param sheet Sheet object representing the specific sheet in the workbook to operate on
	 */
	private static void getAmazonData(Sheet sheet) {
		// initialize a row variable outside the loop
		Row row = null;
		// initialize a header row variable 
	    Row headerRow = null;
	    // Iterator object that will operate on each Row in the sheet
	    Iterator<Row> rowIterator = sheet.iterator(); 
	    // Map to hold the book data <"Header String", "Column Data">
	    Map<String, String> bookData = new HashMap<>();
	     
	    // remove first two rows of spreadsheet (first two rows are blank on Amazon reports)
	    rowIterator.next();
	    rowIterator.remove();
		headerRow = rowIterator.next();
		rowIterator.remove();
	     
		// iterate through each row
	    while (rowIterator.hasNext()) {
	        row = rowIterator.next();
	        
	        // save each row, column data into bookData map under its corresponding header value
	        if (row.getCell(0) != null) {
				for (int j = 0; j < row.getLastCellNum(); j++) {
					if (row.getCell(j).toString() != "") {
						bookData.put(headerRow.getCell(j).toString(), row.getCell(j).toString());
					}
				}
				// pass the bookData Map to a new Book constructor and save the created Book object in the bookList 
				bookList.add(new Book(bookData));
	        }
	        // remove the row after it's been operated on completely
	        rowIterator.remove();
	    }
	}
}
