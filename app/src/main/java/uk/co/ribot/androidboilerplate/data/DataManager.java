package uk.co.ribot.androidboilerplate.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.model.CreateUser;
import uk.co.ribot.androidboilerplate.data.model.Example;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.data.model.User;
import uk.co.ribot.androidboilerplate.data.remote.CreateService;
import uk.co.ribot.androidboilerplate.data.remote.ExampleService;
import uk.co.ribot.androidboilerplate.data.remote.RibotsService;
import uk.co.ribot.androidboilerplate.data.remote.WeatherService;

@Singleton
public class DataManager {

    private final RibotsService mRibotsService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final WeatherService mWeatherService;
    private final ExampleService mExampleService;
    private final CreateService mCreateService;

    @Inject
    public DataManager(RibotsService ribotsService,
                       PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper,
                       WeatherService weatherService,
                       ExampleService exampleService,
                       CreateService createService
    ) {
        mRibotsService = ribotsService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
        mWeatherService = weatherService;
        mExampleService = exampleService;
        mCreateService = createService;

    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Ribot> syncRibots() {
        return mRibotsService.getRibots()
                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
                    @Override
                    public Observable<Ribot> call(List<Ribot> ribots) {
                        return mDatabaseHelper.setRibots(ribots);
                    }
                });
    }

    public Observable<List<Ribot>> getRibots() {
        return mDatabaseHelper.getRibots().distinct();
    }

    public Observable<List<Example>> getExample() {
        return mExampleService.getExample();


    }

    public Observable<String> getWeather() {


        return null;
//
//                mWeatherService.getWeather().map(new Func1<Weather, String>() {
//                    @Override
//                    public String call(Weather weather) {
//                        return weather.getCurrentObservation().getTempC().toString();
//                    }
//                });
    }

    private String resp;

    public Observable<String> createUser(User user) {
        resp=null;

//        mCreateService.getCreate(user).enqueue(new Callback<CreateUser>() {
//            @Override
//            public void onResponse(Call<CreateUser> call, Response<CreateUser> response) {
//                Log.e("response", response.message());
//                resp = response.message();
//
//            }
//
//            @Override
//            public void onFailure(Call<CreateUser> call, Throwable t) {
//                Log.e("response", "ERROR!");
//                resp="ERROR";
//            }
//        });


        return mCreateService.getCreate(user).map(new Func1<CreateUser, String>() {
            @Override
            public String call(CreateUser createUser) {
                return createUser.getStatus();
            }
        });


    }

}
