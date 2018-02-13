package co.edu.ucc.iotandroid.view.presenters;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import co.edu.ucc.iotandroid.domain.RegistryUserBP;
import co.edu.ucc.iotandroid.domain.UseCaseObserver;
import co.edu.ucc.iotandroid.entities.User;
import co.edu.ucc.iotandroid.utils.Analytics;
import co.edu.ucc.iotandroid.view.activities.IRegistryView;

/**
 * Created by jggomez on 10-Jan-18.
 */

public class RegistryPresenter implements IRegistryPresenter {

    private IRegistryView registryView;
    private RegistryUserBP registryUserBP;
    private Context context;

    public RegistryPresenter(IRegistryView registryView, Context context) {
        this.context = context;
        this.registryView = registryView;
        registryUserBP = new RegistryUserBP(new UserObserver());
    }

    @Override
    public void register(String name, String email, String password) {
        try {

            registryView.disableViews();
            registryView.showLoading();

            registryUserBP.registryUser(name, email, password);

            Bundle data = new Bundle();
            data.putString(FirebaseAnalytics.Param.SIGN_UP_METHOD, "email");
            Analytics.getInstance(context).log(data, FirebaseAnalytics.Event.SIGN_UP);

        } catch (Exception ex) {
            registryView.showErrorMessage(ex.getMessage());
        }
    }

    @Override
    public void initialize() {
        registryView.hideLoading();
        registryView.enableViews();
    }

    private final class UserObserver extends UseCaseObserver<User> {

        @Override
        public void onComplete() {
            registryView.hideLoading();
            registryView.enableViews();
        }

        @Override
        public void onNext(User user) {
            registryView.gotoLogin(user);
        }

        @Override
        public void onError(Throwable e) {
            registryView.hideLoading();
            registryView.enableViews();
            registryView.showErrorMessage(e.getMessage());
        }
    }


}
