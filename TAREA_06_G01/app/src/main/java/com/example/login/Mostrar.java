package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.util.Log;
import android.widget.Toast;

public class Mostrar extends AppCompatActivity implements View.OnClickListener{

    daoUsuario dao, dao2;
    Button btnCerrarSesion , btnSalirApp;
Button btnCrearPreferencias;

    TextView tv1;

    ArrayList<String> list, detalle;
    ListView lista;

    Usuario usuario = new Usuario();
    Context context=this;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar);

        btnCerrarSesion=(Button)findViewById(R.id.btCerrarSesion);
        btnCrearPreferencias=(Button)findViewById(R.id.btnPreferencias);
        btnSalirApp=(Button)findViewById(R.id.btnSalirApp);
        tv1=(TextView)findViewById(R.id.lv1);
        btnCerrarSesion.setOnClickListener(this);
        btnSalirApp.setOnClickListener(this);
        lista=(ListView)findViewById(R.id.lista);
        btnCrearPreferencias.setOnClickListener(this);

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
                    "Becado: " + u.getBecado() + "\n");
                    //u.toJsonString() );
        }



        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv1.setText(detalle.get(position));
            }
        });
        int id=0;
        Bundle b =getIntent().getExtras();
        id=b.getInt("id");
        dao2=new daoUsuario(this);
        Usuario u=dao2.getUsuarioById(id);



        SharedPreferences shardPrefs=getSharedPreferences("ArchivoSp",context.MODE_PRIVATE);

        SharedPreferences sharedPref= getPreferences(context.MODE_PRIVATE);
        editor =sharedPref.edit();
        editor.putString("Nombre",u.getNombre());
        editor.putString("Apellido",u.getApellido());
        editor.putString("Correo",u.getCorreo());
        editor.putString("Celular",u.getCelular());
        editor.putString("Becado",u.getBecado());
        editor.putString("FechaNacimiento",u.getFechaNacimiento());


        editor.commit();



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
                editor.clear();
                editor.commit(); // commit changes
                Toast.makeText(this,"Preferencias Compartidas Eliminado ",Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.btnPreferencias:
                SharedPreferences share =getPreferences(context.MODE_PRIVATE);
                String valor = share.getString("Nombre","No hay dato");
                String valor2 = share.getString("Apellido","No hay dato");
                String valor3 = share.getString("Correo","No hay dato");
                String valor4 = share.getString("Celular","No hay dato");
                String valor5 = share.getString("Becado","No hay dato");
                String valor6 = share.getString("FechaNacimiento","No hay dato");

                Toast.makeText(this,"Datos Guardados: "+valor+ "\n" +valor2+ "\n" +valor3+ "\n" +valor4+ "\n" +
                        valor5+ "\n" +valor6,Toast.LENGTH_LONG).show();
                break;

        }
    }

    public void serializeClassGSON(View view) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(usuario);
        IOHelper.writeToFile(this, "cityJsonObj.txt", jsonString);
    }

    public void unserializeClassGSON(View view) {
        Gson gson = new Gson();
        try {
            FileInputStream is = openFileInput("cityJsonObj.txt");
            String result = IOHelper.stringFromStream(is);
            //City city = gson.fromJson(Reader Instance, City.class);
            Usuario usuario = gson.fromJson(result, Usuario.class);
            tv1.setText("Usuario : " + usuario.getId() + "\n");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readJson(View view) {
        String jsonString = IOHelper.stringFromAsset(this, "cities.json");
        try {
            //JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray cities = new JSONArray(jsonString);

            String result = "";
            for (int i = 0; i < cities.length(); i++) {
                JSONObject city = cities.getJSONObject(i);
                //new Gson().fromJson(city.toString(), City.class);
                result += "Country : " + city.getString("country") + "\n" +
                        "Name : " + city.getString("name") + "\n" +
                        "Latitude,Longitud :" + city.getDouble("lat") + ", " + city.getString("lng");
            }
            tv1.setText(result);
        } catch (Exception e) {
            Log.d("ReadPlacesFeedTask", e.getLocalizedMessage());
        }
    }

    public void writeJson(View view) {
        IOHelper.writeToFile(this, "cityJsonObj.txt", usuario.toJsonString());
    }

}