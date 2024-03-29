package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    EditText user,pass;
    Button btnEntrar,btnRegistrar;
    daoUsuario dao;
    TextView tvMensaje;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        user=(EditText)findViewById(R.id.User);
        pass=(EditText)findViewById(R.id.Pass);
        btnEntrar=(Button) findViewById(R.id.btnEntrar);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);
        btnEntrar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        dao=new daoUsuario(this);

        //texto del servicio web y cola del servicio
        tvMensaje = (TextView)findViewById(R.id.txtMensaje);
        queue = Volley.newRequestQueue(this);

        obtenerMensaje();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnEntrar:
                String u=user.getText().toString();
                String p=pass.getText().toString();
                if(u.equals("")&&p.equals("")){
                    Toast.makeText(this,"Error : Campos Vacios",Toast.LENGTH_LONG).show();
                }else if(dao.login(u,p)==1){
                    Usuario ux=dao.getUsuario(u,p);
                    Toast.makeText(this,"Datos Correctos"+"\n"+ "Archivo de Preferencias Compartidas Creado",Toast.LENGTH_LONG).show();
                    Intent c=new Intent(MainActivity.this,Mostrar.class);
                    c.putExtra("id",ux.getId());
                    startActivity(c);
                    //Intent i2=new Intent(MainActivity.this,Inicio.class);


                    //startActivity(i2);
                    //finish();


                }else{
                    Toast.makeText(this,"Usuario o Contraseña incorrectos",Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.btnRegistrar:
                Intent i=new Intent(MainActivity.this,Registrar.class);
            startActivity(i);
            break;

        }
    }

    //metodo para el consumo del servicio web
        private  void obtenerMensaje (){
            String url = "https://grup1ser.herokuapp.com/";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("mensaje");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String msj = jsonObject.getString("msg");


                        tvMensaje.setText(msj);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            queue.add(request);
        }
}
