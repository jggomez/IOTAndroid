package co.edu.ucc.iotandroid.notifications;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by jggomez on 11-Jan-18.
 */

public class IOTInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "IOTInstanceIdService";
    private static final String HOME_TOPIC = "home_alerts";

    /**
     * The Application's current Instance ID token is no longer valid
     * and thus a new one must be requested.
     */
    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FCM Token: " + token);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            if (uid != null && uid != "") {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference("usersHome").child(uid).child("token").setValue(token);
            }
        }

    }
}
