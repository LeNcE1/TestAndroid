package uk.co.ribot.androidboilerplate.ui.user;

import uk.co.ribot.androidboilerplate.data.model.CreateUser;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

public interface UserMvpView extends MvpView {

    void showResponse(CreateUser response);

    void showResponseEmpty();

    void showError();
}
