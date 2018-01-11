package co.edu.ucc.iotandroid.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.edu.ucc.iotandroid.R;
import co.edu.ucc.iotandroid.entities.User;
import co.edu.ucc.iotandroid.view.presenters.RegistryPresenter;

public class RegistryActivity extends AppCompatActivity implements IRegistryView {

    @BindView(R.id.txtNombres)
    EditText namesView;

    @BindView(R.id.txtEmail)
    EditText emailView;

    @BindView(R.id.txtPassword)
    EditText passwordView;

    @BindView(R.id.btnRegistrar)
    Button registerView;

    @BindView(R.id.progress)
    ProgressBar progress;

    private RegistryPresenter registryPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        ButterKnife.bind(this);

        registryPresenter = new RegistryPresenter(this);
        registryPresenter.initialize();
    }

    @OnClick(R.id.btnLogin)
    public void clickLogin() {
        finish();
    }

    @OnClick(R.id.btnRegistrar)
    public void clickRegister() {

        String names = namesView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        register(names, email, password);
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
        namesView.setEnabled(true);
        passwordView.setEnabled(true);
        emailView.setEnabled(true);
        registerView.setEnabled(true);
    }

    @Override
    public void disableViews() {
        namesView.setEnabled(false);
        passwordView.setEnabled(false);
        emailView.setEnabled(false);
        registerView.setEnabled(false);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void register(String name, String email, String password) {
        registryPresenter.register(name, email, password);
    }

    @Override
    public void gotoLogin(User user) {
        Toast.makeText(RegistryActivity.this,
                getString(R.string.mensajeRegistro),
                Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
