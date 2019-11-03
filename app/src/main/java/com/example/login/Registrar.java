package com.example.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



/**
 * Created by Freddy
 */

public class Registrar extends AppCompatActivity implements View.OnClickListener {

    //private CircleImageView fotoPerfil;
    private EditText txtUsuario;
    private EditText txtContrasena;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtCorreo;
    private EditText txtCelular;
    private EditText txtFechaDeNacimiento;
    private RadioButton rdHombre;
    private RadioButton rdMujer;
    private Button btnRegistrar;
    private Button btnCancelar;
    private Switch blBecado;

    //private ImagePicker imagePicker;
    //private CameraImagePicker cameraPicker;

    private String pickerPath;
    private Uri fotoPerfilUri;
    private long fechaDeNacimiento;

    private daoUsuario daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);
        //fotoPerfil = findViewById(R.id.fotoPerfil);
        txtUsuario = findViewById(R.id.RegUsuario);
        txtContrasena = findViewById(R.id.RegPass);
        txtNombre = findViewById(R.id.RegNombre);
        txtApellido = findViewById(R.id.RegApellido);
        txtCorreo = findViewById(R.id.RegMail);
        txtCelular = findViewById(R.id.RegCelular);
        txtFechaDeNacimiento = findViewById(R.id.txtFechaDeNacimiento);
        rdHombre = findViewById(R.id.rdHombre);
        rdMujer = findViewById(R.id.rdMujer);
        blBecado = findViewById(R.id.swBecado);
        btnRegistrar = findViewById(R.id.btnRegistrar2);
        btnCancelar = findViewById(R.id.btnCancelar);


        btnRegistrar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        daoUser = new daoUsuario(this);

        txtFechaDeNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(Registrar.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int mes, int dia) {
                        Calendar calendarResultado = Calendar.getInstance();
                        calendarResultado.set(Calendar.YEAR,year);
                        calendarResultado.set(Calendar.MONTH,mes);
                        calendarResultado.set(Calendar.DAY_OF_MONTH,dia);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date date = calendarResultado.getTime();
                        String fechaDeNacimientoTexto = simpleDateFormat.format(date);
                        fechaDeNacimiento = date.getTime();
                        txtFechaDeNacimiento.setText(fechaDeNacimientoTexto);
                    }
                },calendar.get(Calendar.YEAR)-18,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistrar2:
                Usuario u = new Usuario();
                u.setUsuario(txtUsuario.getText().toString());
                u.setPassword(txtContrasena.getText().toString());
                u.setNombre(txtNombre.getText().toString());
                u.setApellido(txtApellido.getText().toString());
                u.setCelular(txtCelular.getText().toString());
                u.setMail(txtCorreo.getText().toString());
                u.setFechaNacimiento(fechaDeNacimiento);

                final String genero;

                if(rdHombre.isChecked()){
                    genero = "Hombre";
                }else{
                    genero = "Mujer";
                }

                u.setGenero(genero);


                if(!u.isNull()){
                    Toast.makeText(this,"Error:campos vacios",Toast.LENGTH_LONG).show();

                }else if(daoUser.insertarUsuario(u)){
                    Toast.makeText(this,"Registro Exitoso !!",Toast.LENGTH_LONG).show();
                    Intent i2=new Intent(Registrar.this,MainActivity.class);
                    startActivity(i2);
                    finish();

                }else{
                    Toast.makeText(this,"Usuario Ya Registrado!!!",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnCancelar:
                Intent i=new Intent(Registrar.this,MainActivity.class);
                startActivity(i);
                finish();
                break;

        }
    }
}
