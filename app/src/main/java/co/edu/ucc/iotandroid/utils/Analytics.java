package co.edu.ucc.iotandroid.utils;

import android.content.Context;
import android.os.Bundle;

import com.crashlytics.android.answers.FirebaseAnalyticsEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by jggomez on 11-Jan-18.
 */

public class Analytics {


    private static Analytics instance;
    private static FirebaseAnalytics mFirebaseAnalytics;

    private Analytics(){

    }

    public static Analytics getInstance(Context context){

        if(instance == null){
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            instance = new Analytics();
        }

        return instance;

    }

    public void log(Bundle data, String event){
        mFirebaseAnalytics.logEvent(event, data);
    }

}
