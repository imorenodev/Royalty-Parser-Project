import java.util.*;

public class RoyaltyParserClient {

    public static void main(String[] args) {
    		String[] authors = new String[] {"Brittany", "Claire", "Jeanne", "Katrina", "Nicole", "Rebecca"};
  
		List<Book> amazonRawData = RetailerRawData.getData("KDPRoyalties.xlsx");
		
    		for (String author : authors) {
    			String reportName = author.toString() + "'s Amazon Report";
    			Author a = new Author(author.toString());
    			AuthorReport amazonReport = new AuthorReport(reportName, amazonRawData, a.getASINList());
    			System.out.println(a);
    			System.out.println(amazonReport);
    		}
    		
    		new UserGUI();
    }
}