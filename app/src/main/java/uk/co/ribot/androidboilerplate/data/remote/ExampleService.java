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


public interface ExampleService {

    String ENDPOINT = "https://bb-test-server.herokuapp.com/";

    @GET("users.json")
    Observable<List<Example>> getExample();

    class Creator {

        public static ExampleService newExampleService() {
            Gson gson = new GsonBuilder()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())


                    .build();


            return retrofit.create(ExampleService.class);
        }
    }
}
