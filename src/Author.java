import java.util.*;

public class Author {
	private List<String> ASINList = new ArrayList<>();
	private String name = "Author";
	
	public Author(String aName) {
		if (aName != null) {
			name = aName;
			loadSavedASINs();
		}
	}

	public Author(String aName, List<String> asinList) {
		if (aName != null) name = aName;
		ASINList = asinList;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getASINList() {
		return new ArrayList<>(ASINList);
	}
	
	public void setASINList(List<String> asinList) {
		ASINList = asinList;
	}
	
	public boolean removeASIN(String asin) {
		boolean removedSuccessfully = false;
		
		if (asin != null) {
			ASINList.remove(asin);
			removedSuccessfully = true;
		}
		
		return removedSuccessfully;
	}
	
	public boolean removeAllASIN(List<String> asinList) {
		boolean removedSuccessfully = false;
		
		if (asinList != null) {
			ASINList.removeAll(asinList);
		}

		return removedSuccessfully;
	}
	
	public String toString() {
		StringBuilder asinString = new StringBuilder();
		
		for (String s : ASINList) {
			asinString.append((" " + s.toString()));
		}

		return ("Name: " + name + " ASINs:" + asinString);
	}
	
	private void loadSavedASINs() {
		if (name.equals("Claire")) {
			ASINList.add("B00W4DJA5M");
			ASINList.add("B00PJJ4Q8E");
			ASINList.add("B00LKSISBO");
			ASINList.add("B01CIZM03Y");
			ASINList.add("B0194AO264");
			ASINList.add("B0155P6UH6");
			ASINList.add("B00KEFN9W2");
			ASINList.add("B00NH0MUMQ");
			ASINList.add("B00S7R0VKW");
			ASINList.add("B00IKJ1YR6");
			ASINList.add("B01KVYMPGG");
			ASINList.add("B01GP526MS");
			ASINList.add("B01NBQKTUO");
		}
	}
}
