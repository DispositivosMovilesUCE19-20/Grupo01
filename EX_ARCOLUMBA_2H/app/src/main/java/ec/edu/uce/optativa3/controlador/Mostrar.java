package ec.edu.uce.optativa3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ec.edu.uce.optativa3.modelo.Estudiante;
import ec.edu.uce.optativa3.modelo.Log;
import ec.edu.uce.optativa3.modelo.Usuario;
import ec.edu.uce.optativa3.modelo.daoEstudiante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ec.edu.uce.optativa3.modelo.daoLog;
import ec.edu.uce.optativa3.modelo.daoUsuario;
import ec.edu.uce.optativa3.vistas.DataAdapter;




public class Mostrar extends AppCompatActivity implements View.OnClickListener {

    daoEstudiante daoEst;
    daoUsuario daoUser;

    Button btnCerrarSesion, btnSalirApp;
    Button btnCrearPreferencias;
    Button btDescargar;
    Button btnMostarLog;
    ArrayList<Log> logLista;
    daoLog daolog;

    TextView tv1;

    ArrayList<String> list;
    ArrayList<String> detalle;
    ListView lista;
    ArrayList<Estudiante> l;

    //daoEstudiante dao;

    Estudiante estudiante = new Estudiante();
    Context context = this;
    SharedPreferences.Editor editor;

    Usuario u;

    static TextView tvMensaje;
    private static RequestQueue queue;

    public static DataAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar);

        btnMostarLog = (Button)findViewById(R.id.mostarLog) ;

        btnMostarLog.setOnClickListener(this);

        btnCerrarSesion = (Button) findViewById(R.id.btCerrarSesion);
        btnCrearPreferencias = (Button) findViewById(R.id.btnPreferencias);
        btnSalirApp = (Button) findViewById(R.id.btnSalirApp);
        tv1 = (TextView) findViewById(R.id.lv1);
        btnCerrarSesion.setOnClickListener(this);
        btnSalirApp.setOnClickListener(this);
        lista = (ListView) findViewById(R.id.lista);
        btnCrearPreferencias.setOnClickListener(this);
        btDescargar = findViewById(R.id.btDescargar);

        FloatingActionButton my_fab = findViewById(R.id.fab);
        my_fab.setOnClickListener(this);

        tvMensaje = findViewById(R.id.txtMensaje);
        queue = Volley.newRequestQueue(this);

        obtenerDatos("msg");

        list = new ArrayList<String>();


        daoEst = new daoEstudiante(this);

        l = daoEst.selectUsuarios();

        for (Estudiante u : l) {
            //list.add(u.getNombre()+" "+u.getApellido());
            list.add(u.getUsuario());
        }



        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
        lista.setAdapter(a);

        daoUser = new daoUsuario(this);

        ArrayList<Estudiante> l2 = daoEst.selectUsuarios();
        detalle = new ArrayList<>();

        for (Estudiante e : l2) {
            detalle.add(
            "Nombre: " + e.getNombre() + "\n" +
            "Apellido: " + e.getApellido() + "\n" +
            "Correo: " + e.getCorreo() + "\n" +
            "Celular: " + e.getCelular() + "\n" +
            "Cumpleaños: " + e.getFechaNacimiento() + "\n" +
            "Genero: " + e.getGenero() + "\n" +
            "Becado: " + e.getBecado() + "\n");
            //u.toJsonString() );
        }


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv1.setText(detalle.get(position));
            }
        });

        int id = 0;
        Bundle b = getIntent().getExtras();
        id = b.getInt("id");
        daoUser = new daoUsuario(this);
        u = daoUser.getUsuarioById(id);


        SharedPreferences shardPrefs = getSharedPreferences("ArchivoSp", context.MODE_PRIVATE);

        SharedPreferences sharedPref = getPreferences(context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("Usuario", u.getUsuario());

        editor.commit();

        //ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mobileArray);
        //lista.setAdapter(adapter);
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Mostrar.this);
                builder.setTitle("Seleccione una opción");

                String[] options = {"Editar", "Eliminar"};
                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: // Editar

                                //LLAMAR AL METODO UPDATE DEL DAO
                                Intent intent = new Intent(Mostrar.this, activity_form.class);
                                //intent.putExtra("" + arg0.getItemAtPosition(pos), user);

                                intent.putExtra("id", u.getId());
                                startActivity(intent);

                                //obtenerDatos("msg2");

                                break;
                            case 1: // Eliminar

                                //LLAMAR AL METODO DELETE DEL DAO

                                AlertDialog.Builder b = new AlertDialog.Builder(Mostrar.this);
                                b.setMessage("Está seguro que desea eliminar la cuenta??");
                                b.setCancelable(false);

                                b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (daoEst.eliminarUsuario("" + arg0.getItemAtPosition(pos))) {
                                            //Toast.makeText(Mostrar.this, "Se eliminó correctamente", Toast.LENGTH_LONG).show();
                                            obtenerDatos2("msg3");
                                            Intent i2 = new Intent(Mostrar.this, Mostrar.class);
                                            i2.putExtra("id", u.getId());
                                            startActivity(i2);
                                            finish();

                                        } else {
                                           // Toast.makeText(Mostrar.this, "No se eliminó correctamente", Toast.LENGTH_LONG).show();
                                            obtenerDatos2("msg4");
                                        }

                                    }
                                });

                                b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });


                                b.show();


                                //obtenerDatos("msg3");
                                break;
                        }
                    }
                });
                builder.show();

                //Con este recuperas arg0.getItemAtPosition(pos) el ID del objeto señalado
                //Toast.makeText(getApplicationContext(), ""+arg0.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();

                //updateRecyclerView();

                return true;
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSalirApp:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.btCerrarSesion:
                try {
                    mandarDatosServidor();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i2 = new Intent(Mostrar.this, MainActivity.class);
                startActivity(i2);
                editor.clear();
                editor.commit(); // commit changes
                Toast.makeText(this, "Preferencias Compartidas Eliminado ", Toast.LENGTH_LONG).show();

                Intent serviciolog=new Intent(Mostrar.this,ServicioLog.class);
                startService(serviciolog);
              //  try {
                //    mandarABaseDeDatos();
               // } catch (JSONException e) {
                //    e.printStackTrace();
               // }

                finish();
                break;
            case R.id.btnPreferencias:
                SharedPreferences share = getPreferences(context.MODE_PRIVATE);
                String valor = share.getString("Nombre", "No hay dato");
                String valor2 = share.getString("Apellido", "No hay dato");
                String valor3 = share.getString("Correo", "No hay dato");
                String valor4 = share.getString("Celular", "No hay dato");
                String valor5 = share.getString("Becado", "No hay dato");
                String valor6 = share.getString("FechaNacimiento", "No hay dato");

                Toast.makeText(this, "Datos Guardados: " + valor + "\n" + valor2 + "\n" + valor3 + "\n" + valor4 + "\n" +
                        valor5 + "\n" + valor6, Toast.LENGTH_LONG).show();
                break;

            case R.id.fab:
                Intent i = new Intent(Mostrar.this, Registrar.class);
                i.putExtra("id", u.getId());
                startActivity(i);
                break;

            case R.id.mostarLog:



                Intent i3 = new Intent(Mostrar.this, mostrarLog.class);
                startActivity(i3);


                break;
            /*
            case  R.id.btnSensorLuz:
                Intent i5 = new Intent(Mostrar.this, SensorLuz.class);
                startActivity(i5);


                break;

            case R.id.btnMapa:
                Intent i6 = new Intent(Mostrar.this, MapsActivity.class);
                startActivity(i6);

                break;
            */
        }

    }


    public static void updateRecyclerView() {

    }


    public void guardarJson(View view) {
        String carpetaSDCard =
                Environment.getExternalStorageDirectory().getAbsolutePath();
        File carpetaWifi = new File(carpetaSDCard +
                File.separator + "Download" + File.separator + "informacionBD.json");
        try {
            //creamos las carpetas si no existen
            File carpetaWifiDir = new File(carpetaSDCard);
            carpetaWifiDir.mkdirs();

            String texto = "";
            //texto = "//creamos las carpetas si no existen";

            int sizeL = l.size() - 1;
            int contadorL = 0;

            for (Estudiante u : l) {

                if (contadorL == 0) {

                    texto = texto + "[" + "\n" + u.toJsonString() + "," + "\n";

                } else {
                    if (contadorL == sizeL) {
                        //contadorL < sizeL && contadorL != 0
                        texto = texto + u.toJsonString() + "\n" + "]";

                    } else {
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
        } catch (Exception ex) {
            //error, mostrar mensaje
            Toast.makeText(getApplicationContext(),
                    "Error: " + ex.getMessage() + " " + carpetaWifi.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public  static void obtenerDatos(String valorMensaje) {
        String url = "https://grup1ser.herokuapp.com/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("mensaje");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String msj = jsonObject.getString("" + valorMensaje);
                  //  Toast.makeText(getApplicationContext() , "Your Message", Toast.LENGTH_LONG).show();

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


    public  void obtenerDatos2(String valorMensaje) {
        String url = "https://grup1ser.herokuapp.com/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("mensaje");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String msj = jsonObject.getString("" + valorMensaje);
                    Toast.makeText(getApplicationContext() , msj, Toast.LENGTH_LONG).show();

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



    public void mandarDatosServidor() throws JSONException {
        String url = "https://servicio-2.herokuapp.com/";
        String texto = "";
        //texto = "//creamos las carpetas si no existen";

        int sizeL = l.size() - 1;
        int contadorL = 0;

        for (Estudiante u : l) {

            if (contadorL == 0) {

                texto = texto + "[" + "\n" + u.toJsonString() + "," + "\n";

            } else {
                if (contadorL == sizeL) {
                    //contadorL < sizeL && contadorL != 0
                    texto = texto + u.toJsonString() + "\n" + "]";

                } else {
                    texto = texto + u.toJsonString() + "," + "\n";

                }
            }


            //texto = texto + u.toJsonString() + "\n";

            contadorL++;

        }



        JSONArray jsonArr = new JSONArray(texto);



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url  ,jsonArr,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);


    }





    public void mandarABaseDeDatos() throws JSONException {
        String url = "https://restbase1.herokuapp.com/users";
        String texto = "";
        //texto = "//creamos las carpetas si no existen";
        daoLog logdb = new daoLog(getApplicationContext(),null, null,1);
        logLista = logdb.llenarLista();
        String ultimoLista = "";

        android.util.Log.e("Valor del ultimo",String.valueOf(logLista.size()-1));

        if(logLista.size()-1 == -1){
            Log l = new Log();
            l.setFechaIn(null);
            l.setFechaSal(null);
            l.setModelo(null);
            l.setNombre(null);
            l.setUsuario(null);
            l.setVersion(null);
            logdb.Registrar(l);
            logdb.Registrar(l);
            //String ultimoLista = logLista.get(logLista.size()).toString();
            //android.util.Log.e("Valor del JSON",String.valueOf(ultimoLista));

        }else {
            ultimoLista = logLista.get(logLista.size()-1).toJsonString();
            android.util.Log.e("Valor del JSON", String.valueOf(ultimoLista));
            JSONObject jsonArr = new JSONObject(String.valueOf(ultimoLista));
            android.util.Log.e("Valor del JSON FINAL",String.valueOf(jsonArr));

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonArr, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //TODO: handle success
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    //TODO: handle failure
                }
            });

            Volley.newRequestQueue(this).add(jsonRequest);
        }




    }





}