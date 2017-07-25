package uk.co.ribot.androidboilerplate.ui.login;

import java.util.List;


import uk.co.ribot.androidboilerplate.data.model.Weather;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;


public interface LoginMvpView extends MvpView {

    void showWeather(String weather);

    void showWeatherEmpty();

    void showError();
}
