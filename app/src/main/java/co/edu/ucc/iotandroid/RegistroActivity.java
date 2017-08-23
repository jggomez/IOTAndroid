package co.edu.ucc.iotandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistroActivity extends AppCompatActivity {

    @BindView(R.id.txtNombres)
    EditText txtNombres;

    @BindView(R.id.txtEmail)
    EditText txtEmail;

    @BindView(R.id.txtPassword)
    EditText txtPassword;

    @BindView(R.id.btnRegistrar)
    Button btnRegistrar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.btnRegistrar)
    public void clickRegistrar(){

        String nombres = txtNombres.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        btnRegistrar.setEnabled(false);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(RegistroActivity.this,
                                    ControlActivity.class);

                            startActivity(intent);

                            finish();

                            return;
                        }

                        Toast.makeText(RegistroActivity.this,
                                "Error "+ task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
