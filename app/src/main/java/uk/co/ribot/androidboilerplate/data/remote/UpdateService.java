package uk.co.ribot.androidboilerplate.data.remote;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import uk.co.ribot.androidboilerplate.data.model.CreateUser;
import uk.co.ribot.androidboilerplate.data.model.User;

public interface UpdateService {

    //неверный url

    String ENDPOINT = "https://bb-test-server.herokuapp.com/";

    //    @POST("users/1.json")
//    Observable<CreateUser> postUpdate(@Body JSONObject jj);
    @POST("users/1.json")
    Observable<CreateUser> postUpdate(@Body User user);

    class Creator {

        public static UpdateService newUpdateService() {
            Gson gson = new GsonBuilder()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();


            return retrofit.create(UpdateService.class);
        }


    }

}
