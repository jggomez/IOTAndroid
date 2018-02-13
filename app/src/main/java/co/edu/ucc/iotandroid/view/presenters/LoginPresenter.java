package co.edu.ucc.iotandroid.view.presenters;

import android.content.Context;
import android.content.SharedPreferences;

import co.edu.ucc.iotandroid.R;
import co.edu.ucc.iotandroid.domain.LoginUserBP;
import co.edu.ucc.iotandroid.domain.UseCaseObserver;
import co.edu.ucc.iotandroid.entities.User;
import co.edu.ucc.iotandroid.view.activities.ILoginView;

/**
 * Created by jggomez on 10-Jan-18.
 */

public class LoginPresenter implements ILoginPresenter {

    private ILoginView loginView;
    private LoginUserBP loginUserBP;
    private Context context;

    public LoginPresenter(ILoginView loginView, Context context) {
        this.context = context;
        this.loginView = loginView;
        loginUserBP = new LoginUserBP(
                new UserObserver(),
                new HomeObserver(),
                new LoginObserver()
        );
    }

    @Override
    public void login(String email, String password) {
        try {
            loginView.showLoading();
            loginView.disableViews();

            loginUserBP.login(email, password);

        } catch (Exception ex) {
            loginView.showErrorMessage(ex.getMessage());
        }

    }

    @Override
    public void initialize() {
        loginView.hideLoading();
        loginView.enableViews();
    }

    public boolean isEmailVerified() {
        boolean resp = loginUserBP.isEmailVerified();

        if (!resp) {
            loginView.showErrorMessage(context.getString(R.string.emailVerified));
        }

        return resp;
    }

    public boolean isUserLogIn() {
        return loginUserBP.isUserLogIn();
    }

    public String getUserNameUserLogIn() {
        SharedPreferences preferences = this.context.getSharedPreferences("user", this.context.MODE_PRIVATE);
        return preferences.getString("userName", "");
    }


    private void saveUser(User user) {
        SharedPreferences preferences = this.context.getSharedPreferences("user", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", user.getNames());
        editor.apply();
    }

    private void saveHome(String homeId) {
        SharedPreferences preferences = this.context.getSharedPreferences("home", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("homeId", homeId);
        editor.apply();
    }

    private final class LoginObserver extends UseCaseObserver<Boolean> {

        @Override
        public void onComplete() {
            loginView.hideLoading();
            loginView.enableViews();
        }

        @Override
        public void onNext(Boolean result) {
            if (!isEmailVerified()) {
                return;
            }

            loginUserBP.getUserAuth();
        }

        @Override
        public void onError(Throwable e) {
            loginView.hideLoading();
            loginView.enableViews();
            loginView.showErrorMessage(e.getMessage());
        }
    }

    private final class UserObserver extends UseCaseObserver<User> {

        @Override
        public void onComplete() {
            loginView.hideLoading();
            loginView.enableViews();
        }

        @Override
        public void onNext(User user) {
            saveUser(user);
            loginUserBP.validateHomeByUser();
        }

        @Override
        public void onError(Throwable e) {
            loginView.hideLoading();
            loginView.enableViews();
            loginView.showErrorMessage(e.getMessage());
        }
    }

    private final class HomeObserver extends UseCaseObserver<String> {

        @Override
        public void onComplete() {
            loginView.hideLoading();
            loginView.enableViews();
        }

        @Override
        public void onNext(String homeId) {
            if (homeId == null) {
                loginView.showErrorMessage(context.getString(R.string.mensajeRegistro));
                return;
            }

            saveHome(homeId);
            loginView.goToMain();
        }

        @Override
        public void onError(Throwable e) {
            loginView.hideLoading();
            loginView.enableViews();
            loginView.showErrorMessage(e.getMessage());
        }
    }


}
