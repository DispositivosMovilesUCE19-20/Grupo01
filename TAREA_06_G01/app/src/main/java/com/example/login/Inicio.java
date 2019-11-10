package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Inicio extends AppCompatActivity implements View.OnClickListener {
Button btnMostrar , btnSalir;
TextView nombre;
int id=0;
Usuario u;
daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        nombre=(TextView)findViewById(R.id.nombreUsuario);
        btnMostrar=(Button)findViewById(R.id.btnMostar);
        btnSalir=(Button)findViewById(R.id.btSalir);
        btnMostrar.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
        Bundle b =getIntent().getExtras();
        id=b.getInt("id");
        dao=new daoUsuario(this);
        u=dao.getUsuarioById(id);
        nombre.setText(u.getNombre()+" "+u.getApellido());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMostar:
                Intent c=new Intent(Inicio.this,Mostrar.class);
                startActivity(c);
                break;
            case R.id.btSalir:
                Intent i2=new Intent(Inicio.this,MainActivity.class);
                startActivity(i2);
                finish();
                break;

        }
    }
}
