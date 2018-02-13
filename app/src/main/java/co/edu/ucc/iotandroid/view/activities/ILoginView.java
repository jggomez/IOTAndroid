package co.edu.ucc.iotandroid.view.activities;

import co.edu.ucc.iotandroid.utils.IView;

/**
 * Created by jggomez on 10-Jan-18.
 */

public interface ILoginView extends IView {

    void login(String user, String password);

    void goToMain();

}
