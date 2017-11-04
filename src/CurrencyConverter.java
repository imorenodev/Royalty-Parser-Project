import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
	public static Map<String, Double> conversionMap = new HashMap<>();
	
	public static double getConversion(String currencyType, double royalty) {
		double conversionFactor = conversionMap.containsKey(currencyType) ? conversionMap.get(currencyType) : 0.0;
		return conversionFactor * royalty;
	}
	
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
