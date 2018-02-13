package co.edu.ucc.iotandroid.view.activities;

import co.edu.ucc.iotandroid.entities.User;
import co.edu.ucc.iotandroid.utils.IView;

/**
 * Created by jggomez on 10-Jan-18.
 */

public interface IRegistryView extends IView {

    void register(String name, String user, String password);

    void gotoLogin(User user);
}
