package co.edu.ucc.iotandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txtNomUsuario)
    EditText txtUsuario;

    @BindView(R.id.txtPassword)
    EditText txtPassword;

    @BindView(R.id.btnIngresar)
    Button btnIngresar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.registrar)
    public void clickRegistrar() {

        startActivity(new Intent(LoginActivity.this, RegistroActivity.class));

    }

    @OnClick(R.id.btnIngresar)
    public void clickIngresar() {

        auth.signInWithEmailAndPassword(txtUsuario.getText().toString(),
                txtPassword.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(LoginActivity.this,
                                ControlActivity.class);

                        startActivity(intent);

                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,
                        "Error " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
