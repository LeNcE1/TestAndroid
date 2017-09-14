package uk.co.ribot.androidboilerplate.ui.user;

import uk.co.ribot.androidboilerplate.ui.base.MvpView;

public interface UserMvpView extends MvpView {

    void showResponse(String response);

    void showResponseEmpty();

    void showError();
}
