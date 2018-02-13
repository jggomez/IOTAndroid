package co.edu.ucc.iotandroid.utils;

/**
 * Created by jggomez on 10-Jan-18.
 */

public interface IView {

    void showLoading();

    void hideLoading();

    void enableViews();

    void disableViews();

    void showErrorMessage(String message);

}
