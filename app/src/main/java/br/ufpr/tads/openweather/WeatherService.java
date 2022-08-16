package br.ufpr.tads.openweather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("weather")
    Call<Weather> getWeather(@Query("q") String cityCountry, @Query("units") String units, @Query("appid") String apiKey);
}
