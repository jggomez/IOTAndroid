package co.edu.ucc.iotandroid;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ControlActivity extends AppCompatActivity {

    private int estadoSala = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        ButterKnife.bind(this);

        String nomUsuario = getIntent().getStringExtra("nomUsuario");

        Log.i("ControlActivity", nomUsuario);

        setTitle("Bienvenido " + nomUsuario);

    }

    @OnClick(R.id.btnSala)
    public void clickSala(View view) {

        if (estadoSala == 0) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            estadoSala = 1;
        } else {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
            estadoSala = 0;
        }
    }
}
