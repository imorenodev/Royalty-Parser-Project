import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
	public static Map<String, Double> conversionMap = new HashMap<>();

	public static double getConversion(String currencyType, double royalty) {
		double conversionFactor = conversionMap.containsKey(currencyType) ? conversionMap.get(currencyType) : 0.0;
		return conversionFactor * royalty;
	}
}
