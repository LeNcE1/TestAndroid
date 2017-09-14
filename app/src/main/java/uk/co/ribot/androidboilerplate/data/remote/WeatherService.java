package uk.co.ribot.androidboilerplate.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import uk.co.ribot.androidboilerplate.data.model.Example;


public interface WeatherService {
    String ENDPOINT = "http://api.wunderground.com/api/";

    @GET("be31627b09934c1e/conditions/q/CA/San_Francisco.json")
    Observable<List<Example>> getWeather();

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static WeatherService newWeatherService() {
            Gson gson = new GsonBuilder()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())


                    .build();


            return retrofit.create(WeatherService.class);
        }
    }
}
