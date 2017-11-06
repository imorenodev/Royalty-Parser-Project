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

public class RetailerRawData {
	private static List<Book> bookList = new ArrayList<>();

	public static List<Book> getData(String filePath) {
		InputStream input = null;
		try {
			input = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(input);

			// if Amazon Royalty SpreadSheet then
			getAmazonData(wb.getSheetAt(0));

		} catch (InvalidFormatException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}
		return bookList;
	}	

	private static void getAmazonData(Sheet sheet) {
		 Row row = null;
	     Row headerRow = null;
	     Iterator<Row> rowIterator = sheet.iterator(); 
	     Map<String, String> bookData = new HashMap<>();
	     
	     // remove first two rows of spreadsheet
	     rowIterator.next();
	     rowIterator.remove();
	     	headerRow = rowIterator.next();
			rowIterator.remove();
	     
	     while (rowIterator.hasNext()) {
	         row = rowIterator.next();
	         
	         if (row.getCell(0) != null) {
					for (int j = 0; j < row.getLastCellNum(); j++) {
						if (row.getCell(j).toString() != "") {
							bookData.put(headerRow.getCell(j).toString(), row.getCell(j).toString());
						}
					}
					bookList.add(new Book(bookData));
	         }
	         rowIterator.remove();
	     }
	
		/** remove empty lines
		for (int i = 0; i < 3; i++) {
				//bookList.remove(bookList.size() - 1);
		}*/
	}
}
