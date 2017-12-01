import java.util.HashMap;
import java.util.Map;

/**
 * @purpose Book class specifies the instance fields and constructor for the Book object
 * @author Ian Moreno 
 * @date	 12/01/2017
 */

public class Book {
	// Map to contain each book's corresponding data
	private Map<String, String> bookDataMap = new HashMap<>();
	// initialize the header strings for each book's data item
	private String Title = "",
			   Author ="",
			   ASIN = "",
			   Marketplace = "",
			   UnitsSold = "",
			   UnitsRefunded ="",
			   NetUnitsSold ="",
			   RoyaltyType ="",
			   TransactionType ="",
			   Currency= "",
			   AvgListPriceWithoutTax ="",
			   AvgOfferPriceWithoutTax ="",
			   AvgFileSize ="",
			   AvgDeliveryCost ="",
			   Royalty ="";    		
		
	// constructor takes in a Map of the book's data and assigns each data value to it's corresponding Book instance variable
	public Book(Map<String, String> bookData) {
		// save deep copy of the bookData Map
		bookDataMap = new HashMap<String,String>(bookData);
		Title = bookData.get("Title");
		Author = bookData.get("Author");
		ASIN = bookData.get("ASIN"); 
		Marketplace = bookData.get("MarketPlace");
		UnitsSold = bookData.get("Units Sold");
		UnitsRefunded = bookData.get("Units Refunded"); 
		NetUnitsSold = bookData.get("Net Units Sold");
		RoyaltyType = bookData.get("Royalty Type");
		TransactionType = bookData.get("Transaction Type");
		Currency = bookData.get("Currency");
		AvgListPriceWithoutTax = bookData.get("Avg. List Price Without Tax");
		AvgOfferPriceWithoutTax = bookData.get("Avg. Offer Price Without Tax");
		AvgFileSize = bookData.get("Avg. File Size");
		AvgDeliveryCost = bookData.get("Avg. Delivery Cost");
		Royalty = bookData.get("Royalty");
	}
	
	/**
	 * @purpose public helper method returns a deep copy of the book's bookDataMap
	 * @return getBookdataMap Map<String, String> of the book's bookDataMap
	 */
	public Map<String, String> getBookDataMap() {
		// return deep copy of the bookDataMap
		return new HashMap<String, String>(bookDataMap);
	}
	
	/**
	 * @purpose public method returns the unique ASIN of the book
	 * @return ASIN String variable representing the book's unqiue ASIN
	 */
	public String getASIN() {
		return ASIN;
	}
	
	/**
	 * @purpose Overridden toString method returns more meaningful information about the Book object
	 * @return String value representing the Title, Author, and Royalties information of the Book object
	 */
	public String toString() {
		return ("Title: " + Title + " " +
				"Author: " + Author + " " + 
				"Royalty: " + Royalty);
	}
}
