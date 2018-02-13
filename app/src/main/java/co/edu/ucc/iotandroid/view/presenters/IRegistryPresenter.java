package co.edu.ucc.iotandroid.view.presenters;

/**
 * Created by jggomez on 10-Jan-18.
 */

public interface IRegistryPresenter {

    void register(String name, String email, String password);

    void initialize();
}
