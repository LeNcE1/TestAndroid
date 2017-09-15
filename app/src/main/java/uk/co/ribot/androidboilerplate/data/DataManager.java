package uk.co.ribot.androidboilerplate.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import uk.co.ribot.androidboilerplate.data.model.CreateUser;
import uk.co.ribot.androidboilerplate.data.model.Example;
import uk.co.ribot.androidboilerplate.data.model.User;
import uk.co.ribot.androidboilerplate.data.model.Users;
import uk.co.ribot.androidboilerplate.data.remote.CreateService;
import uk.co.ribot.androidboilerplate.data.remote.ExampleService;
import uk.co.ribot.androidboilerplate.data.remote.UpdateService;

@Singleton
public class DataManager {


    private final ExampleService mExampleService;
    private final CreateService mCreateService;
    private final UpdateService mUpdateService;

    @Inject
    public DataManager(
                       ExampleService exampleService,
                       CreateService createService,
                       UpdateService updateService
    ) {

        mExampleService = exampleService;
        mCreateService = createService;
        mUpdateService =updateService;

    }







    public Observable<List<Example>> getExample() {
        return mExampleService.getExample();


    }





    public Observable<CreateUser> createUser(User user) {



        return mCreateService.postCreate(new Users(user));


    }

    public Observable<CreateUser> updateUser(User user) {

    return mUpdateService.postUpdate(user);
}

}
