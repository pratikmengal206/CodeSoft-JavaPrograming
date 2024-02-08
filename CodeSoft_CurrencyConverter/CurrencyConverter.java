package CodeSoft_CurrencyConverter;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Currency Selection
        System.out.println("Enter the base currency code (e.g., USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.println("Enter the target currency code (e.g., EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        // Step 2: Fetch real-time exchange rates
        double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);

        // Step 3: Amount Input
        System.out.println("Enter the amount to convert: ");
        double amount = scanner.nextDouble();

        // Step 4: Currency Conversion
        double convertedAmount = convertCurrency(amount, exchangeRate);

        // Step 5: Display Result
        System.out.printf("%s %.2f = %s %.2f%n", baseCurrency, amount, targetCurrency, convertedAmount);

        scanner.close();
    }

    private static double getExchangeRate(String baseCurrency, String targetCurrency) {
    String apiUrl = "https://api.exchangerate-api.com/v4/latest/" + baseCurrency;

    try {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(apiUrl);

        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() == 200) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            System.out.println("Raw JSON response: " + jsonResponse);

            JSONObject rates = new JSONObject(jsonResponse).getJSONObject("rates");
            return rates.getDouble(targetCurrency);
        } else {
            System.out.println("Failed to fetch exchange rates. Please try again later.");
            System.exit(1);
        }
    } catch (IOException | JSONException e) {
        e.printStackTrace();
    }

    return 0.0;
}


    private static double convertCurrency(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }
}
