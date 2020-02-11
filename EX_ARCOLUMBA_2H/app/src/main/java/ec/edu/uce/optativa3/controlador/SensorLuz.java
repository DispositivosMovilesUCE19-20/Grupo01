package ec.edu.uce.optativa3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.R;

import static android.hardware.Sensor.TYPE_LIGHT;

public class SensorLuz extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor LightSensor;
    private SensorEventListener lightEventListener ;
    private View root;
    private float maxValue;
    TextView valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_luz);


        root= findViewById(R.id.root);
        valor = (TextView)findViewById(R.id.valorlum);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        LightSensor = sensorManager.getDefaultSensor(TYPE_LIGHT);

        if(LightSensor ==null){
            Toast.makeText(this,"El dispositivo no tiene sensor de luz",Toast.LENGTH_SHORT).show();
            finish();

        }
        maxValue=LightSensor.getMaximumRange();

        lightEventListener= new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float value= event.values[0];
              //  getSupportActionBar().setTitle("Luminosidad : "+value+ "lx");
                valor.setText("Luminosidad : "+value+" lx");

                int newValue = (int) (255f * (value*5) /maxValue);
                root.setBackgroundColor(Color.rgb(newValue,newValue,newValue));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListener,LightSensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }
}
