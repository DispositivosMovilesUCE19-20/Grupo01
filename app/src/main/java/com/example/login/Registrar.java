package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Registrar extends AppCompatActivity implements View.OnClickListener {
EditText us,pass,nom,ap,cel,mail, fechaNacimiento;
Button reg,can, btSalir;
RadioGroup rgGenero;
daoUsuario dao;
private RadioButton rdHombre;
private RadioButton rdMujer;

private long fechaDeNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);

        us = findViewById(R.id.RegUsuario);
        pass = findViewById(R.id.RegPass);
        nom = findViewById(R.id.RegNombre);
        ap = findViewById(R.id.RegApellido);
        cel = findViewById(R.id.RegCelular);
        mail = findViewById(R.id.RegMail);
        fechaNacimiento = findViewById(R.id.txtFechaDeNacimiento);
        rgGenero = findViewById(R.id.rgGenero);

        reg = findViewById(R.id.btnRegistrar2);
        can = findViewById(R.id.btnCancelar);

        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
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
                        fechaNacimiento.setText(fechaDeNacimientoTexto);
                    }
                },calendar.get(Calendar.YEAR)-23,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        reg.setOnClickListener(this);
        can.setOnClickListener(this);
        dao=new daoUsuario(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistrar2:
                Usuario u = new Usuario();
                u.setUsuario(us.getText().toString());
                u.setPassword(pass.getText().toString());
                u.setNombre(nom.getText().toString());
                u.setApellido(ap.getText().toString());
                u.setCelular(cel.getText().toString());
                u.setMail(cel.getText().toString());

                final String genero;

                if(rdHombre.isChecked()){
                    genero = "Hombre";
                }else{
                    genero = "Mujer";
                }

                u.setGenero(genero);
                u.setFechaNacimiento(fechaDeNacimiento);

                if(!u.isNull()){
                    Toast.makeText(this,"Error:campos vacios",Toast.LENGTH_LONG).show();

                }else if(dao.insertarUsuario(u)){
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
