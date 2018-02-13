package co.edu.ucc.iotandroid.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.edu.ucc.iotandroid.R;
import co.edu.ucc.iotandroid.entities.Hogar;
import co.edu.ucc.iotandroid.utils.Analytics;

public class ControlActivity extends AppCompatActivity {

    private static int estadoSala = 0;
    private static int estadoCocina = 0;
    private static int estadoBano = 0;
    private static int estadoHabitacion = 0;
    private Analytics analytics;

    @BindView(R.id.btnSala)
    Button btnSala;

    @BindView(R.id.btnHabitacion)
    Button btnHabitacion;

    @BindView(R.id.btnBano)
    Button btnBano;

    @BindView(R.id.btnCocina)
    Button btnCocina;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        ButterKnife.bind(this);

        analytics = Analytics.getInstance(getApplicationContext());

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        String username = preferences.getString("userName", "");

        setTitle("Bienvenido " + username);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        preferences = getSharedPreferences("home", MODE_PRIVATE);
        String homeId = preferences.getString("homeId", "");

        databaseReference = database.getReference("home").child(homeId);

        loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.signOut) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            firebaseAuth.signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    private void loadData() {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Hogar hogar = dataSnapshot.getValue(Hogar.class);

                setEstadoBoton(btnBano, hogar.getBano());
                setEstadoBoton(btnCocina, hogar.getCocina());
                setEstadoBoton(btnHabitacion, hogar.getHabitacion());
                setEstadoBoton(btnSala, hogar.getSala());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setEstadoBoton(Button boton, int estado) {
        if (estado == 1) {
            boton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        } else {
            boton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
        }
    }

    enum lugaresEnum {
        LIVING,
        KITCHEN,
        ROOM,
        BATHROOM
    }

    private void logAnalytic(String place) {
        Bundle data = new Bundle();
        data.putString(FirebaseAnalytics.Param.ITEM_NAME, place);
        analytics.log(data, FirebaseAnalytics.Event.VIEW_ITEM);
    }

    @OnClick(R.id.btnSala)
    public void clickSala(View view) {

        if (estadoSala == 0) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            estadoSala = 1;
            logAnalytic(lugaresEnum.LIVING.name());
        } else {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
            estadoSala = 0;
        }

        actualizarData(lugaresEnum.LIVING, estadoSala);
    }

    @OnClick(R.id.btnCocina)
    public void clickCocina(View view) {

        if (estadoCocina == 0) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            estadoCocina = 1;
            logAnalytic(lugaresEnum.KITCHEN.name());
        } else {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
            estadoCocina = 0;
        }

        actualizarData(lugaresEnum.KITCHEN, estadoCocina);
    }

    @OnClick(R.id.btnBano)
    public void clickBano(View view) {

        if (estadoBano == 0) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            estadoBano = 1;
            logAnalytic(lugaresEnum.BATHROOM.name());
        } else {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
            estadoBano = 0;
        }

        actualizarData(lugaresEnum.BATHROOM, estadoBano);
    }

    @OnClick(R.id.btnHabitacion)
    public void clickHabitacion(View view) {

        if (estadoHabitacion == 0) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            estadoHabitacion = 1;
            logAnalytic(lugaresEnum.ROOM.name());
        } else {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
            estadoHabitacion = 0;
        }

        actualizarData(lugaresEnum.ROOM, estadoHabitacion);
    }

    private void actualizarData(lugaresEnum lugares, int data) {

        databaseReference.child(lugares.name().toLowerCase()).setValue(data);

    }
}
