package org.ie.openweathermap;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class WeatherApiClient {
    private static final String API_KEY = "4dcfaef59338731aa622eaefd993c1d2";

    public static String getWeatherData(String latitude, String longitude) throws Exception {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" +
                latitude + "&lon=" + longitude + "&appid=" + API_KEY;
        System.out.println("apiUrl: " + apiUrl);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(apiUrl);
        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() == 200) {
            return EntityUtils.toString(response.getEntity());

        } else {
            throw new RuntimeException(
                    "Failed to retrieve weather data. Status code: " + response.getStatusLine().getStatusCode());
        }
    }
}
