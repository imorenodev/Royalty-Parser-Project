import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.io.File;

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
    		}
	}
}
