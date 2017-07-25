package uk.co.ribot.androidboilerplate.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.SyncService;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.util.NetworkUtil;

public class LoginActivity extends BaseActivity implements LoginMvpView {
    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.login.LoginActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    LoginPresenter loginPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.buttonCreate)
    TextView buttonCreate;
    @BindView(R.id.toolbarLayout)
    LinearLayout toolbarLayout;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.mail)
    EditText mail;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.buttonHelp)
    ImageView buttonHelp;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent().inject(this);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        loginPresenter.attachView(this);


        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }

        buttonLogin.setEnabled(false);

        Observable<String> loginObservable = TextValidation.getTextWatcherObservable(mail);
        Observable<String> passObservable = TextValidation.getTextWatcherObservable(pass);

        Observable.combineLatest(loginObservable, passObservable,
                new Func2<String, String, Boolean>() {

                    @Override
                    public Boolean call(@NonNull String s, @NonNull String s2) {


                if(!TextValidation.checkMail(s)||!TextValidation.checkPass(s2))
                    return false;
                else return true;
                    }
                })

                .subscribe(new Action1<Boolean>() {

                    @Override
                    public void call(@NonNull Boolean aBoolean) {
                        buttonLogin.setEnabled(aBoolean);

                    }
                });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        loginPresenter.detachView();
    }

    @Override
    public void showWeather(String weather) {
        Snackbar.make(getCurrentFocus(), "Температура воздуха "+weather+" °C", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showWeatherEmpty() {
        Snackbar.make(getCurrentFocus(), "Empty", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        Snackbar.make(getCurrentFocus(), "Error", Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.buttonLogin)
    public void onLoginClicked() {
        if(NetworkUtil.isNetworkConnected(this))
        loginPresenter.loadWeather();
        else
            Snackbar.make(getCurrentFocus(), "Нет подключения к интернету", Snackbar.LENGTH_LONG).show();

    }
}
