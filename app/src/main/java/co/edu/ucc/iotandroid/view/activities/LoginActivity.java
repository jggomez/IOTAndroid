package co.edu.ucc.iotandroid.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.edu.ucc.iotandroid.R;
import co.edu.ucc.iotandroid.view.presenters.ILoginPresenter;
import co.edu.ucc.iotandroid.view.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.txtEmail)
    EditText emailView;

    @BindView(R.id.txtPassword)
    EditText passwordView;

    @BindView(R.id.btnIngresar)
    Button ingresarView;

    @BindView(R.id.progress)
    ProgressBar progress;

    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        //Crashlytics.getInstance().crash();

        loginPresenter = new LoginPresenter(this, getApplicationContext());
        loginPresenter.initialize();

        isUserLogIn();
    }

    private void isUserLogIn() {

        boolean result = loginPresenter.isUserLogIn();

        if (result) {

            if(!loginPresenter.isEmailVerified()){
                return;
            }

            Intent intent = new Intent(LoginActivity.this,
                    ControlActivity.class);

            intent.putExtra("userName",
                    loginPresenter.getUserNameUserLogIn());

            startActivity(intent);

            finish();
        }

    }

    @OnClick(R.id.registrar)
    public void clickRegistrar() {

        startActivity(new Intent(LoginActivity.this, RegistryActivity.class));

    }

    @OnClick(R.id.btnIngresar)
    public void clickIngresar() {
        login(emailView.getText().toString(), passwordView.getText().toString());
    }


    @Override
    public void showLoading() {
        progress.setVisibility(View.GONE);

    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void enableViews() {
        emailView.setEnabled(true);
        passwordView.setEnabled(true);
        ingresarView.setEnabled(true);
    }

    @Override
    public void disableViews() {
        emailView.setEnabled(false);
        passwordView.setEnabled(false);
        ingresarView.setEnabled(false);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login(String email, String password) {
        Crashlytics.log("Log In User");
        loginPresenter.login(email, password);
    }

    @Override
    public void goToMain() {
        startActivity(new Intent(this, ControlActivity.class));
        finish();
    }
}
