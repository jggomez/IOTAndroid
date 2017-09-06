package co.edu.ucc.iotandroid;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ControlActivity extends AppCompatActivity {

    private static int estadoSala = 0;
    private static int estadoCocina = 0;
    private static int estadoBano = 0;
    private static int estadoHabitacion = 0;

    @BindView(R.id.btnSala)
    Button btnSala;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        ButterKnife.bind(this);

        String nomUsuario = getIntent().getStringExtra("nomUsuario");

        Log.i("ControlActivity", nomUsuario);

        setTitle("Bienvenido " + nomUsuario);

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("hogar");

    }

    enum lugaresEnum {
        SALA,
        COCINA,
        HABITACION,
        BANO
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

        actualizarData(lugaresEnum.SALA, estadoSala);
    }

    @OnClick(R.id.btnCocina)
    public void clickCocina(View view) {

        if (estadoCocina == 0) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            estadoCocina = 1;
        } else {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
            estadoCocina = 0;
        }

        actualizarData(lugaresEnum.COCINA, estadoCocina);
    }

    @OnClick(R.id.btnBano)
    public void clickBano(View view) {

        if (estadoBano == 0) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            estadoBano = 1;
        } else {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
            estadoBano = 0;
        }

        actualizarData(lugaresEnum.BANO, estadoBano);
    }

    @OnClick(R.id.btnHabitacion)
    public void clickHabitacion(View view) {

        if (estadoHabitacion == 0) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            estadoHabitacion = 1;
        } else {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
            estadoHabitacion = 0;
        }

        actualizarData(lugaresEnum.HABITACION, estadoHabitacion);
    }

    private void actualizarData(lugaresEnum lugares, int data) {

        databaseReference.child(lugares.name().toLowerCase()).setValue(data);

    }
}
