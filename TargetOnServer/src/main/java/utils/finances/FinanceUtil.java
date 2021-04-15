package utils.finances;

import constants.Keys;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.json.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FinanceUtil {

    private static final String BASE_CODE = "base_code";
    private static final String TARGET_CODE = "target_code";
    private static final String CONVERSION_RATE = "conversion_rate";
    final String api = "https://v6.exchangerate-api.com/v6/";
    final String key = "0fca7d38746559b122122a32";
    private final JSONParser parser = new JSONParser();

    public JsonObject getExchangeRate(String base, String currency) throws IOException, ParseException {
        URL url = new URL(buildUrl(api, key, base, currency));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        System.out.println(status);
        final JsonObject jsonObject = new JsonObject(parser.parse(new InputStreamReader(con.getInputStream())));
        jsonObject.put(Keys.RESPONSE_CODE, status);
        return jsonObject;
    }
    private String buildUrl(String api, String key, String base, String currency) {
        return api + Keys.SLASH + key + Keys.SLASH + Keys.PAIR + Keys.SLASH + base + Keys.SLASH + currency;
    }

    public static void main(String[] args) throws IOException, ParseException {
        FinanceUtil financeUtil = new FinanceUtil();
        final String c1 = "USD";
        final String c2 = "UAH";
        final JsonObject exchange = financeUtil.getExchangeRate(c2, c1);
        final String base = exchange.getString(BASE_CODE);
        final String target = exchange.getString(TARGET_CODE);
        final float rate = exchange.getFloat(CONVERSION_RATE);

        System.out.println(base + "/" + target + "=" + rate);
    }
}
