import java.util.*;

/**
 * @author 	Ian Moreno
 * @date		11/13/2017
 * @purpose The Royalty Parser Client is the driver program for the Royalty Parser application that takes in
 * 			a set of unique Amazon Kindle Direct Publishing Royalty Report spreadsheets, parses the data,
 * 			and then allows the user to create an individual customized output report for each author.
 * 			The output reports contain only the book records that correspond to that specific author.
 */

public class RoyaltyParserClient {

    public static void main(String[] args) {
    		// list of pre-saved authors
    		String[] authors = new String[] {"Brittany", "Claire", "Jeanne", "Katrina", "Nicole", "Rebecca"};
    		ArrayList<Author> authorsList = new ArrayList<>();
  
    		// build list of pre-saved authors
    		for (String author : authors) {
    			Author a = new Author(author.toString());
    			authorsList.add(a);
    		}
    		
    		// present GUI to user
    		new UserGUI(authorsList);
    }
}