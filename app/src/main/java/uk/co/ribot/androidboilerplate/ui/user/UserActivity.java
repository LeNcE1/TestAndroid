package uk.co.ribot.androidboilerplate.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.SyncService;
import uk.co.ribot.androidboilerplate.data.model.CreateUser;
import uk.co.ribot.androidboilerplate.data.model.User;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.ui.main.MainActivity;
import uk.co.ribot.androidboilerplate.util.DialogFactory;

public class UserActivity extends BaseActivity implements UserMvpView {
    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.user.UserActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    UserPresenter userPresenter;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.button)
    Button button;

    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        userPresenter.attachView(this);
        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }


        if (getIntent().getIntExtra("Id", -1) == -1) {
            button.setText("Create");

            avatar.setImageResource(R.drawable.ic_face_black_48dp);
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFactory.createGenericErrorDialog(v.getContext(),
                            "здесь должно было быть добавление аватара, но что то пошло не так")
                            .show();

                }
            });


        } else {
            button.setText("Change");
            firstName.setText(getIntent().getStringExtra("FirstName"));
            lastName.setText(getIntent().getStringExtra("LastName"));
            email.setText(getIntent().getStringExtra("Email"));
            avatar.setClickable(false);
            if (!getIntent().getStringExtra("AvatarUrl").isEmpty()) {
                Picasso.with(this)
                        .load(getIntent().getStringExtra("AvatarUrl"))
                        .placeholder(R.drawable.ic_face_black_48dp)

                        .fit()
                        .centerCrop()

                        .into(avatar);

            } else avatar.setVisibility(View.GONE);

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        userPresenter.detachView();
    }


    @OnClick(R.id.button)
    public void onButtonClicked() {
        if (firstName.getText().length() > 0 &&
                lastName.getText().length() > 0 &&
                new TextValidation().checkMail(email.getText().toString())) {


            if (button.getText().equals("Create")) {
                userPresenter.createUser(
                        new User(firstName.getText().toString(),
                                lastName.getText().toString(),
                                email.getText().toString(),
                                ""));

            }
            if (button.getText().equals("Change")) {
                userPresenter.updateUser(new User(firstName.getText().toString(),
                        lastName.getText().toString(),
                        email.getText().toString(),
                        getIntent().getStringExtra("AvatarUrl")));

            }
        } else {
            Toast.makeText(this, "поля заполнены не верно", Toast.LENGTH_SHORT).show();
            Log.e("ErrorInput","ErrorInput");
        }
    }


    @Override
    public void showResponse(CreateUser response) {


        Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));

    }

    @Override
    public void showResponseEmpty() {
        Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }
}
