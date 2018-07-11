package golife.com.gojektest.api.dto.res;

import java.util.List;

/**
 * Created by Swetarani Panda on 7/5/2018.
 */

public class WeatherRes {

    private Location location;
    private Current current;
    private Forecast forecast;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public class Current {

        public Double getTemp_c() {
            return temp_c;
        }

        public void setTemp_c(Double temp_c) {
            this.temp_c = temp_c;
        }

        public Double getTemp_f() {
            return temp_f;
        }

        public void setTemp_f(Double temp_f) {
            this.temp_f = temp_f;
        }

        private Double temp_c;
        private Double temp_f;


    }

    public class Astro {

        private String sunrise;
        private String sunset;
        private String moonrise;
        private String moonset;

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getMoonrise() {
            return moonrise;
        }

        public void setMoonrise(String moonrise) {
            this.moonrise = moonrise;
        }

        public String getMoonset() {
            return moonset;
        }

        public void setMoonset(String moonset) {
            this.moonset = moonset;
        }

    }

    class Condition {

        private String text;
        private String icon;
        private Integer code;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

    }

    class Condition_ {

        private String text;
        private String icon;
        private Integer code;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

    }


    public class Day {

        private Float maxtemp_c;
        private Float maxtemp_f;
        private Float mintemp_c;

        public Float getMaxtemp_c() {
            return maxtemp_c;
        }

        public void setMaxtemp_c(Float maxtemp_c) {
            this.maxtemp_c = maxtemp_c;
        }

        public Float getMaxtemp_f() {
            return maxtemp_f;
        }

        public void setMaxtemp_f(Float maxtemp_f) {
            this.maxtemp_f = maxtemp_f;
        }

        public Float getMintemp_c() {
            return mintemp_c;
        }

        public void setMintemp_c(Float mintemp_c) {
            this.mintemp_c = mintemp_c;
        }

        public Float getMintemp_f() {
            return mintemp_f;
        }

        public void setMintemp_f(Float mintemp_f) {
            this.mintemp_f = mintemp_f;
        }

        public Float getAvgtemp_c() {
            return avgtemp_c;
        }

        public void setAvgtemp_c(Float avgtemp_c) {
            this.avgtemp_c = avgtemp_c;
        }

        public Float getAvgtemp_f() {
            return avgtemp_f;
        }

        public void setAvgtemp_f(Float avgtemp_f) {
            this.avgtemp_f = avgtemp_f;
        }

        private Float mintemp_f;
        private Float avgtemp_c;
        private Float avgtemp_f;


    }

    public class Forecast {

        private List<Forecastday> forecastday;

        public List<Forecastday> getForecastday() {
            return forecastday;
        }

        public void setForecastday(List<Forecastday> forecastday) {
            this.forecastday = forecastday;
        }

    }

    public class Forecastday {

        private String date;
        private Day day;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }


        public Day getDay() {
            return day;
        }

        public void setDay(Day day) {
            this.day = day;
        }


    }

    public class Location {

        private String name;
        /*private String region;
        private String country;
        private Double lat;
        private Double lon;
        private String tzId;
        private Integer localtimeEpoch;
        private String localtime;*/

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

      /*  public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public String getTzId() {
            return tzId;
        }

        public void setTzId(String tzId) {
            this.tzId = tzId;
        }

        public Integer getLocaltimeEpoch() {
            return localtimeEpoch;
        }

        public void setLocaltimeEpoch(Integer localtimeEpoch) {
            this.localtimeEpoch = localtimeEpoch;
        }

        public String getLocaltime() {
            return localtime;
        }

        public void setLocaltime(String localtime) {
            this.localtime = localtime;
        }
*/
    }

}