package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Mostrar extends AppCompatActivity implements View.OnClickListener{
    ListView lista;
    daoUsuario dao, dao2;
    Button btnCerrarSesion , btnSalirApp;
    TextView tv1;
    ArrayList<String> list, detalle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar);
        btnCerrarSesion=(Button)findViewById(R.id.btCerrarSesion);
        btnSalirApp=(Button)findViewById(R.id.btnSalirApp);
        tv1=(TextView)findViewById(R.id.lv1);
        btnCerrarSesion.setOnClickListener(this);
        btnSalirApp.setOnClickListener(this);
        lista=(ListView)findViewById(R.id.lista);


        dao=new daoUsuario(this);
        ArrayList<Usuario> l= dao.selectUsuarios();
        list=new ArrayList<String>();
        for (Usuario u:l){
            list.add(u.getNombre()+" "+u.getApellido());
        }
        ArrayAdapter<String> a=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list );
        lista.setAdapter(a);

        dao2=new daoUsuario(this);
        ArrayList<Usuario> l2= dao2.selectUsuarios();
        detalle=new ArrayList<String>();
        for (Usuario u:l2){
            detalle.add("Usuario: " + u.getUsuario() + "\n" +
            "Correo: " + u.getCorreo() + "\n" +
                            "Celular: " + u.getCelular() + "\n" +
                    "FechaNacimiento: " + u.getFechaNacimiento() + "\n" +
                    "Genero: " + u.getGenero() + "\n" +
                    "Becado: " + u.getBecado());
        }



        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv1.setText(detalle.get(position));
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSalirApp:
                Intent intent =new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.btCerrarSesion:
                Intent i2 = new Intent(Mostrar.this, MainActivity.class);
                startActivity(i2);
                finish();
                break;

        }
    }
}