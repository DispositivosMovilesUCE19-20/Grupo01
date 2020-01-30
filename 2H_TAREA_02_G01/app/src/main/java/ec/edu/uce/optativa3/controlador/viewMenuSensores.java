package ec.edu.uce.optativa3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.login.R;

public class viewMenuSensores extends AppCompatActivity implements View.OnClickListener {

    Button btnLuz, btnGeo, btnRegresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu_sensores);


        btnGeo = findViewById(R.id.buttonMenuGeo);
        btnLuz = findViewById(R.id.buttonMenuLuz);
        btnRegresar = findViewById(R.id.buttonRegresarMenuSensor);

        btnGeo.setOnClickListener(this);
        btnLuz.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonMenuGeo:
                Intent iGeo = new Intent(this, MapsActivity.class);
                startActivity(iGeo);
                break;
            case R.id.buttonMenuLuz:
                Intent iLuz = new Intent(this, SensorLuz.class);
                startActivity(iLuz);
                break;

            case R.id.buttonRegresarMenuSensor:
                Intent iRegresar = new Intent(this, MainActivity.class);
                startActivity(iRegresar);
                break;
        }


    }
}
