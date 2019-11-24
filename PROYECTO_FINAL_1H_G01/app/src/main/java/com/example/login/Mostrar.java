package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.util.ListIterator;

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

import ec.edu.uce.optativa3.modelo.Usuario;
import ec.edu.uce.optativa3.vistas.DataAdapter;
import ec.edu.uce.optativa3.controlador.activity_form;


public class Mostrar extends AppCompatActivity implements View.OnClickListener{

    daoUsuario dao, dao2;
    Button btnCerrarSesion , btnSalirApp;
    Button btnCrearPreferencias;
    Button btDescargar;

    TextView tv1;

    ArrayList<String> list, detalle;
    ListView lista;
    ArrayList<Usuario> l;


    Usuario usuario = new Usuario();
    Context context=this;
    SharedPreferences.Editor editor;

    TextView tvMensaje;
    private RequestQueue queue;

    public static DataAdapter adapter;


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
        btDescargar = findViewById(R.id.btDescargar);

        dao=new daoUsuario(this);
        l = dao.selectUsuarios();

        tvMensaje = (TextView)findViewById(R.id.txtMensaje);
        queue = Volley.newRequestQueue(this);

        obtenerDatos("msg");

        list=new ArrayList<String>();

        for (Usuario u:l){
            list.add(u.getNombre()+" "+u.getApellido());
        }

        ArrayAdapter<String> a=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list );
        lista.setAdapter(a);

        dao2=new daoUsuario(this);
        ArrayList<Usuario> l2= dao2.selectUsuarios();
        detalle = new ArrayList<String>();
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

        //ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mobileArray);
        //lista.setAdapter(adapter);
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {


                //Toast.makeText(getApplicationContext(), "POS: " + pos, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(Mostrar.this);
                builder.setTitle("Seleccione una opción");

                String[] options = {"Editar", "Eliminar"};
                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: // Editar
                                //Intent intent = new Intent(Mostrar.this, activity_form.class);
                                //intent.putExtra("position", position);
                                //startActivity(intent);
                                obtenerDatos("msg2");

                                break;
                            case 1: // Eliminar
                                //vehiculos.remove(position);
                                //System.out.println("Size: " + vehiculos.size());
                                //adapter.notifyDataSetChanged();
                                obtenerDatos("msg3");
                                break;
                        }
                    }
                });
                builder.show();


                return true;
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

    public void guardarJson(View view)
    {
        String carpetaSDCard =
                Environment.getExternalStorageDirectory().getAbsolutePath();
        File carpetaWifi = new File (carpetaSDCard +
                File.separator + "Download" + File.separator + "informacionBD.json");
        try
        {
            //creamos las carpetas si no existen
            File carpetaWifiDir = new File (carpetaSDCard);
            carpetaWifiDir.mkdirs();

            String texto = "";
            //texto = "//creamos las carpetas si no existen";

            int sizeL = l.size() -1;
            int contadorL = 0;

            for (Usuario u:l){

                if(contadorL == 0){

                    texto = texto + "[" + "\n" + u.toJsonString() + "," + "\n";

                }else{
                    if(contadorL == sizeL){
                        //contadorL < sizeL && contadorL != 0
                        texto = texto + u.toJsonString() + "\n" + "]";

                    }
                    else{
                        texto = texto + u.toJsonString() + "," + "\n";

                    }
                }


                //texto = texto + u.toJsonString() + "\n";

                contadorL++;

            }

            byte bTexto[] = texto.getBytes();
            FileOutputStream ficSalida = new FileOutputStream(carpetaWifi);
            ficSalida.write(bTexto);
            ficSalida.close();
            Toast.makeText(getApplicationContext(),
                    "Datos Usuarios guardados en fichero: " +
                            carpetaWifi.toString(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            //error, mostrar mensaje
            Toast.makeText(getApplicationContext(),
                    "Error: " + ex.getMessage() + " " + carpetaWifi.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerDatos(String valorMensaje){
        String url = "https://grup1ser.herokuapp.com/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("mensaje");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String msj = jsonObject.getString("" + valorMensaje);


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