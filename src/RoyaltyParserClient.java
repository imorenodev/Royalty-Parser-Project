import java.util.*;

public class RoyaltyParserClient {

    public static void main(String[] args) {
    		// pre-saved authors
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