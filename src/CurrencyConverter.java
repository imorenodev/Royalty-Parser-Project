import java.util.HashMap;
import java.util.Map;

/**
 * @purpose CurrencyConverter class applies the current foreign exchange rates to the currency raw data
 * @author Ian Moreno
 * @date 12/1/2017 
 */

public class CurrencyConverter {
	// Map from the currency abbreviation to it's corresponding exchange rate
	public static Map<String, Double> conversionMap = new HashMap<>();
	
	/**
	 * @purpose public static method allows user to pass in a currency type's abbreviation and the USD value to be converted
	 * @param currencyType String variable containing the currency abbreviation
	 * @param royalty Double value in USD to be converted to the currencyType
	 * @return Double value of the converted royalty value (conversionFactor * USD royalty)
	 */
	public static double getConversion(String currencyType, double royalty) {
		// assign conversionFactor to the currencyType's corresponding factor found in the conversionMap
		double conversionFactor = conversionMap.containsKey(currencyType) ? conversionMap.get(currencyType) : 0.0;
		// returnt the converted value
		return conversionFactor * royalty;
	}
	
	/**
	 * @purpose public static method allows user to reset the conversionMap  
	 */
	public static void resetConversionMap() {
		// initialize conversionMap with default values
		CurrencyConverter.conversionMap.put("USD", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("CAD", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("GBP", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("EUR", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("BRL", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("INR", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("JPY", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("AUD", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("MXN", Double.valueOf(1.0));
	}
}
