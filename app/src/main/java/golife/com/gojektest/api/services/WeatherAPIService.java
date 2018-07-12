package golife.com.gojektest.api.services;

import com.squareup.okhttp.Callback;

import java.io.IOException;

import golife.com.gojektest.api.config.OKHTTPClient;


/**
 * Created by Swetarani Panda on 11/4/2017.
 */

public class WeatherAPIService {
    private static WeatherAPIService directoryAPIService = null;

    public static WeatherAPIService getInstance() {
        if (directoryAPIService == null) {
            synchronized (WeatherAPIService.class) {
                if (directoryAPIService == null) {
                    directoryAPIService = new WeatherAPIService();
                    return directoryAPIService;
                }
            }
        }
        return directoryAPIService;
    }


    public void getWeatherData(String apikey,String locality,int days, Callback callback) throws IOException {
       String WEB_SERVER_URL = "http://api.apixu.com/v1/forecast.json";

        try {
            OKHTTPClient.get(WEB_SERVER_URL + "?key="+apikey+"&q="+locality+"&days="+days, callback);
        } catch (Exception e) {
        }
    }

}
