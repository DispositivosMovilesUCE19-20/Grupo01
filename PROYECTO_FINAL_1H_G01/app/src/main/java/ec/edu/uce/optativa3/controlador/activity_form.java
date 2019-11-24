package ec.edu.uce.optativa3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class activity_form extends AppCompatActivity {

    private EditText txtUser;
    private EditText txtMail;
    private EditText txtCelular;

    // Comentar dependiendo de con cual se trabaja
    private Switch wsBecado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        txtUser = findViewById(R.id.txt_user);
        txtMail = findViewById(R.id.txt_mail);
        txtCelular = findViewById(R.id.txt_celular);

        // todo
        wsBecado = findViewById(R.id.sw_becado);




    }


}
