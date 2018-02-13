package co.edu.ucc.iotandroid.domain;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.edu.ucc.iotandroid.entities.User;

/**
 * Created by jggomez on 10-Jan-18.
 */

public class LoginUserBP {

    private UseCaseObserver<User> useCaseObserver;
    private UseCaseObserver<String> useCaseObserverHome;
    private UseCaseObserver<Boolean> useCaseObserverLogin;

    private FirebaseAuth auth;
    private DatabaseReference referenceUsers;
    private DatabaseReference referenceHome;

    public LoginUserBP(UseCaseObserver<User> useCaseObserver,
                       UseCaseObserver<String> useCaseObserverHome,
                       UseCaseObserver<Boolean> useCaseObserverLogin) {
        this.useCaseObserver = useCaseObserver;
        this.useCaseObserverHome = useCaseObserverHome;
        this.useCaseObserverLogin = useCaseObserverLogin;
        auth = FirebaseAuth.getInstance();
        referenceUsers = FirebaseDatabase.getInstance().getReference("users");
        referenceHome = FirebaseDatabase.getInstance().getReference("usersHome");
    }

    public boolean isUserLogIn(){
        return auth.getCurrentUser() != null;
    }

    public boolean isEmailVerified(){
        return auth.getCurrentUser().isEmailVerified();
    }

    public void getUserAuth() {
        try {

            String uid = auth.getCurrentUser().getUid();

            referenceUsers.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    useCaseObserver.onNext(user);
                    useCaseObserver.onComplete();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    useCaseObserver.onError(databaseError.toException());
                }
            });

        } catch (Exception ex) {
            useCaseObserver.onError(ex);
        }

    }

    public void validateHomeByUser() {

        try {

            String uid = auth.getCurrentUser().getUid();

            referenceHome.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() == 0) {
                        useCaseObserverHome.onNext(null);
                        useCaseObserverHome.onComplete();
                    } else {
                        String homeId = dataSnapshot.getChildren()
                                .iterator().next().getValue(String.class);

                        useCaseObserverHome.onNext(homeId);
                        useCaseObserverHome.onComplete();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    useCaseObserverHome.onError(databaseError.toException());
                }
            });

        } catch (Exception ex) {
            useCaseObserverHome.onError(ex);
        }

    }

    public void login(String email, String password) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        useCaseObserverLogin.onNext(true);
                        useCaseObserverLogin.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        useCaseObserverLogin.onError(e);
                    }
                });
    }


}
