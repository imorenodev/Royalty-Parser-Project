import java.util.*;

/**
 * @purpose Author class specifies the author objects
 * @author Ian Moreno
 * @date 12/1/2017
 */

public class Author {
	// List of the author's ASINs
	private List<String> ASINList = new ArrayList<>();
	// Author's name
	private String name = "Author";
	
	/**
	 * @purpose constructor initializes the author's name and load's their corresponding saved ASIN list if it exists
	 * @param aName String variable representing the Author's name
	 */
	public Author(String aName) {
		if (aName != null) {
			name = aName;
			loadSavedASINs();
		}
	}

	/**
	 * @purpose constructor initializes the author's name and assigns their ASIN list to the passed in argument
	 * @param aName String variable representing the Author's name
	 * @param asinList List of ASINs belonging to the author
	 */
	public Author(String aName, List<String> asinList) {
		if (aName != null) name = aName;
		ASINList = asinList;
	}
	
	/**
	 * @purpose public method returns the author's name
	 * @return String variable name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @purpose public method returns a deep copy of the author's ASINList
	 * @return ArrayList deep copy of the author's ASINList
	 */
	public List<String> getASINList() {
		return new ArrayList<>(ASINList);
	}
	
	/**
	 * @purpose setter method sets the author's ASINList
	 * @param asinList List of ASINs belonging to the author
	 */
	public void setASINList(List<String> asinList) {
		ASINList = asinList;
	}
	
	/**
	 * @purpose public method allows user to remove a single ASIN from the author's ASINList
	 * @param asin String variable representing the ASIN to be removed from the author's ASINList
	 * @return boolean value true if removal successful, otherwise false
	 */
	public boolean removeASIN(String asin) {
		boolean removedSuccessfully = false;
		
		if (asin != null) {
			ASINList.remove(asin);
			removedSuccessfully = true;
		}
		
		return removedSuccessfully;
	}
	
	/**
	 * @purpose public method allows user to remove a list of ASIN values from the author's ASINList
	 * @param asinList List of the ASINs to be removed from the author's ASINList
	 * @return boolean value true if removeAll successful, otherwise false
	 */
	public boolean removeAllASIN(List<String> asinList) {
		boolean removedSuccessfully = false;
		
		if (asinList != null) {
			ASINList.removeAll(asinList);
		}

		return removedSuccessfully;
	}
	
	/**
	 * @purpose overridden toString method to provide more meaningful information about Author object
	 */
	public String toString() {
		// mutable StringBuilder object
		StringBuilder asinString = new StringBuilder();
		
		// for each ASIN in the author's ASINList, append to the stringbuilder object
		for (String s : ASINList) {
			asinString.append((" " + s.toString()));
		}

		// return the .toString() value of the stringbuilder object
		return ("Name: " + name + " ASINs:" + asinString);
	}
	
	/**
	 * @purpose private helper method contains hard-coded, pre-saved author ASIN data
	 * 			builds the author's ASINList when the Author object is created and the name matches
	 * 			a pre-saved author name. Mostly for demonstration purposes.
	 */
	private void loadSavedASINs() {
		if (name.equalsIgnoreCase("Claire")) {
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
		else if (name.equalsIgnoreCase("Katrina")) {
			ASINList.add("B01IO9TIII");
			ASINList.add("B00NQIKGLG");
			ASINList.add("B01FEI4OAE");
			ASINList.add("B00UT0FNYE");
			ASINList.add("B00H1CPXP2");
			ASINList.add("B00P8FU2NC");
			ASINList.add("B00GFYO2EK");
			ASINList.add("B00I1I1RO6");
			ASINList.add("B00H8CJOSM");
			ASINList.add("B00K7J786S");
			ASINList.add("B00IU1RDHE");
			ASINList.add("B00M5Q8S8I");
			ASINList.add("B01MXFS2RN");
			ASINList.add("B01BXGMSW2");
			ASINList.add("B017XYOM2C");
			ASINList.add("B011HEI19O");
			ASINList.add("B00REOVTAA");
			ASINList.add("B014LRBCBI");
			ASINList.add("B00FJ8YX8I");
			ASINList.add("B00CU4S25Y");
			ASINList.add("B06XZ6J7BT");
			ASINList.add("B00DSADFKQ");
		}
		else if (name.equalsIgnoreCase("Rebecca")) {
			ASINList.add("B00R1O4J0A");
			ASINList.add("B00O5P9L1U");
			ASINList.add("B00K0Z1CHU");
			ASINList.add("B00F8PHUVO");
			ASINList.add("B00FP445UC");
			ASINList.add("B00ICB1JEA");
			ASINList.add("B015GC5254");
			ASINList.add("B015GD0D1Q");
			ASINList.add("B015GECYA8");
			ASINList.add("B015OVB56W");
			ASINList.add("B015PFHPCU");
			ASINList.add("B00LYM5BBQ");
			ASINList.add("B00GPY5UIW");
			ASINList.add("B00C83JLS4");
			ASINList.add("B00U0NBGAU");
			ASINList.add("B00D8DKW0O");
			ASINList.add("B008P30J8W");
			ASINList.add("B00CBNM6PQ");
			ASINList.add("B00B61ULQK");
			ASINList.add("B00BR9IXQQ");
			ASINList.add("B0091YXH56");
			ASINList.add("B009AEUI18");
			ASINList.add("B009QQHJBM");
			ASINList.add("B00BQR3YY0");
			ASINList.add("B009KXKO9A");
			ASINList.add("B009HFOISY");
			ASINList.add("B009MD5P8I");
			ASINList.add("B008KP9WAG");
			ASINList.add("B00946XXGE");
			ASINList.add("B00C10LZME");
			ASINList.add("B00C0KVHVE");
			ASINList.add("B00C83JM38");
			ASINList.add("B00C0KVF6Q");
			ASINList.add("B015NMZC2A");
			ASINList.add("B00CLZKP5W");
			ASINList.add("B009QQHSJ0");
			ASINList.add("B00A95CZ9U");
		}
		else if (name.equalsIgnoreCase("Brittany")) {
			ASINList.add("B00TVN74X8");
			ASINList.add("B017GGHJ40");
			ASINList.add("B00IG9MH0S");
			ASINList.add("B00X09L2OG");
			ASINList.add("B00QCLLA8W");
			ASINList.add("B016VAQO7A");
			ASINList.add("B00MYGPS3W");
			ASINList.add("B01I2JJ0Q0");
			ASINList.add("B01E6LIKSC");
		}
		else if (name.equalsIgnoreCase("Jeanne")) {
			ASINList.add("B00R6VNRMO");
			ASINList.add("B00MRO6LNM");
			ASINList.add("B00YT22C7E");
			ASINList.add("B0157J6B2O");
			ASINList.add("B00IMKD22I");
			ASINList.add("B00KSQDA7G");
		}
		else if (name.equalsIgnoreCase("Nicole")) {
			ASINList.add("B00OSUYTNM");
			ASINList.add("B00KHTMFDE");
			ASINList.add("B00IA9T8JW");
			ASINList.add("B00R1OU0QM");
			ASINList.add("B00SUJG2OG");
			ASINList.add("B00UGE4GC8");
			ASINList.add("B00YB9S904");
			ASINList.add("B012PWX57U");
		}
	}
}
