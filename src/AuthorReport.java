import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * @purpose AuthorReport class creates an individual customized royalty report for an author
 * @author Ian Moreno 
 * @date 12/1/2017
 */

public class AuthorReport {
	// List of the author's book
	List<Book> authorsBookList = new ArrayList<>();
	String reportName = "";
	
	/**
	 * @purpose constructor initializes the report name and builds the authors book list
	 * @param aReportName String variable representing the name of the report
	 * @param rawDataBookList List of raw book data parsed from the Amazon spreadsheet
	 * @param asins List of the author's corresponding ASINs
	 */
	public AuthorReport(String aReportName, List<Book> rawDataBookList, List<String> asins) {
		reportName = aReportName;
		buildAuthorsBookList(rawDataBookList, asins);
	}
	
	/**
	 * @purpose public method returns a list of the authors corresponding books
	 * @return List of Books representing the author's books
	 */
	public List<Book> getAuthorReport() {
		return authorsBookList;
	}

	/**
	 * @purpose private helper method takes a List of the raw book data parsed from the spreadsheet 
	 * 			and a list of the author's ASINs and creates a list of the books belonging to the author
	 * @param rawDataBookList List of the raw data parsed from the Amazon spreadhseet
	 * @param asins List of the author's corresponding ASINs
	 */
	private void buildAuthorsBookList(List<Book> rawDataBookList, List<String> asins) {
		// for each ASIN in the authors list of ASINs
		for (String asin : asins) {
			// and for each Book in the raw spreadsheet data
			for (Book book : rawDataBookList) {
				// if the book's ASIN matches the author's ASIN then add book to the author's book List
				if (book.getASIN().equals(asin)) {
					authorsBookList.add(book);
				}
			}
		}
	}
	
	/**
	 * @purpose public method overrides toString to provide more meaningful information about the AuthorReport object
	 * @return String value containing the author's report name, and a list of the books in the author's book list
	 */
	public String toString() {
		StringBuilder bookListStrings = new StringBuilder();
		
		for (Book book : authorsBookList) {
			bookListStrings.append(book + "\n");
		}

		return "Report Name: " + reportName  + "\n"
							  + bookListStrings;
	}
}
