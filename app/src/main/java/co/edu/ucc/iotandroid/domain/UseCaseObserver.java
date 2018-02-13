package co.edu.ucc.iotandroid.domain;

/**
 * Created by jggomez on 10-Jan-18.
 */

public abstract class UseCaseObserver<T> {

    public void onComplete() {
    }

    public void onError(Throwable e) {
    }

    public void onNext(T t) {
    }

}
