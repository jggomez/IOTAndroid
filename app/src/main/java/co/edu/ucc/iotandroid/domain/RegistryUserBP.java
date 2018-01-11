package co.edu.ucc.iotandroid.domain;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import co.edu.ucc.iotandroid.entities.User;

/**
 * Created by jggomez on 10-Jan-18.
 */

public class RegistryUserBP {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private UseCaseObserver<User> useCaseObserver;


    public RegistryUserBP(UseCaseObserver<User> useCaseObserver) {

        this.useCaseObserver = useCaseObserver;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

    }

    public void registryUser(final String names, String email, String password) {

        final User objUsuario = new User();
        objUsuario.setNames(names);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final String uid = task.getResult().getUser().getUid();

                            String tokenFCM = FirebaseInstanceId.getInstance().getToken();
                            objUsuario.setToken(tokenFCM);

                            reference.child(uid).setValue(objUsuario)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                useCaseObserver.onNext(objUsuario);
                                                auth.getCurrentUser().sendEmailVerification();

                                            } else {
                                                useCaseObserver.onError(task.getException());
                                            }

                                            useCaseObserver.onComplete();
                                        }
                                    });


                        } else {
                            useCaseObserver.onError(task.getException());
                        }
                    }
                });
    }
}
