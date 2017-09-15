package uk.co.ribot.androidboilerplate.ui.user;

import android.util.Log;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.CreateUser;
import uk.co.ribot.androidboilerplate.data.model.User;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;
import uk.co.ribot.androidboilerplate.util.RxUtil;


public class UserPresenter extends BasePresenter<UserMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public UserPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(UserMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();

    }

    public void createUser(User user) {

//        String response = mDataManager.createUser(user);
//        if (response == null) {
//            getMvpView().showResponseEmpty();
//        } else {
//            if (response.equals("ERROR")) {
//                getMvpView().showError();
//            } else
//
//                getMvpView().showResponse(response);
//        }


        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.createUser(user)
                .observeOn(AndroidSchedulers.mainThread())

                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CreateUser>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        Log.e("e= ",e.toString());
                        getMvpView().showError();
                    }


                    @Override
                    public void onNext(CreateUser response) {
                        Log.e("CreateUser",response.getError()+" "+response.getStatus());
                        if (response == null) {
                            getMvpView().showResponseEmpty();
                        } else {

                            getMvpView().showResponse(response);
                        }
                    }
                });
    }

    public void updateUser(User user) {




        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.updateUser(user)
                .observeOn(AndroidSchedulers.mainThread())

                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CreateUser>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        Log.e("e= ",e.toString());
                        getMvpView().showError();
                    }


                    @Override
                    public void onNext(CreateUser response) {
                        Log.e("CreateUser",response.getError()+" "+response.getStatus());
                        if (response == null) {
                            getMvpView().showResponseEmpty();
                        } else {

                            getMvpView().showResponse(response);
                        }
                    }
                });
    }
}
