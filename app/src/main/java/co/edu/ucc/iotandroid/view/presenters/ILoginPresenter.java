package co.edu.ucc.iotandroid.view.presenters;

/**
 * Created by jggomez on 10-Jan-18.
 */

public interface ILoginPresenter {

    void login(String email, String password);

    void initialize();

    boolean isUserLogIn();

    String getUserNameUserLogIn();

    boolean isEmailVerified();

}
