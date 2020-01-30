package ec.edu.uce.optativa3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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
import com.example.login.R;

import ec.edu.uce.optativa3.modelo.Usuario;
import ec.edu.uce.optativa3.modelo.daoUsuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static String fecha1;
    static String getUsuario;
    EditText user, pass;
    Button btnEntrar, btnRegistrar, btnSensores;
    daoUsuario dao;
    TextView tvMensaje;
    private RequestQueue queue;

    TextView grabar;

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        permisoParaEscribirArchivos();

        user = (EditText) findViewById(R.id.User);
        pass = (EditText) findViewById(R.id.Pass);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnSensores = findViewById(R.id.btnSensores);
        btnEntrar.setOnClickListener(this);
        btnSensores.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        dao = new daoUsuario(this);

        grabar = findViewById(R.id.grabar);

        //texto del servicio web y cola del servicio
        tvMensaje = (TextView) findViewById(R.id.txtMensaje);
        queue = Volley.newRequestQueue(this);

        obtenerMensaje();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEntrar:

                String u = user.getText().toString();
                String p = pass.getText().toString();

                //Login
                logIn("" + u,"" + p);

                break;
            case R.id.btnRegistrar:
                Intent i = new Intent(MainActivity.this, RegistroUsuario.class);
                startActivity(i);
                break;

            case R.id.btnSensores:
                Intent iVoz = new Intent(this, viewMenuSensores.class);
                startActivity(iVoz);
                break;
        }
    }

    public void logIn(String u, String p){
        if (u.equals("") && p.equals("")) {
            Toast.makeText(this, "Error : Campos Vacios", Toast.LENGTH_LONG).show();
        } else if (dao.login(u, p) == 1) {

            fecha1=fechaIn();

            getUsuario=u;

            Usuario ux = dao.getUsuario(u, p);
            Toast.makeText(this, "Datos Correctos" + "\n" + "Archivo de Preferencias Compartidas Creado", Toast.LENGTH_LONG).show();
            Intent c = new Intent(this, Mostrar.class);
            c.putExtra("id", ux.getId());
            //System.out.println("Imprimir valor envio: " + ux.getId());
            startActivity(c);

            //Intent im = new Intent(MainActivity.this, Registrar.class);
            //im.putExtra("id", ux.getId());
            //startActivity(im);


        } else {
            Toast.makeText(this, "Estudiante o Contraseña incorrectos", Toast.LENGTH_LONG).show();

        }
    }


    public void onClickImgBtnHablar(View v) {
        Intent intentActionRecognizeSpeech = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Configura el Lenguaje (Español-México)
        intentActionRecognizeSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
        try {
            startActivityForResult(intentActionRecognizeSpeech,
                    RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Tú dispositivo no soporta el reconocimiento por voz",
                    Toast.LENGTH_SHORT).show();
        }
    }


    //metodo para el consumo del servicio web
    private void obtenerMensaje() {
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

    private void permisoParaEscribirArchivos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String requiredPermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

            if (checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{requiredPermission}, 101);
            }
        }

    }

    public String fechaIn(){
        Date objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate

        String strDateFormat = "hh: mm: ss a dd-MMM-yyyy"; // El formato de fecha está especificado
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); // La cadena de formato de fecha se pasa como un argumento al objeto
        // System.out.println(objSDF.format(objDate)); // El formato de fecha se aplica a la fecha actual

        String fechaHoraIn=objSDF.format(objDate);


        return fechaHoraIn;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> speech = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);

                    strSpeech2Text = strSpeech2Text.replace(" ","")
                            .replace("á","a")
                            .replace("é","e")
                            .replace("í","i")
                            .replace("ó","o")
                            .replace("ú","u")
                            .toLowerCase();

                    Usuario userVoice = dao.getUsuarioByUser("" + strSpeech2Text);

                    //System.out.println(userVoice.getUsuario() +  "   "  + userVoice.getContrasena());

                    if (userVoice != null){
                        logIn("" + userVoice.getUsuario(),"" + userVoice.getContrasena());
                    }else{
                        Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_LONG).show();
                    }

                    grabar.setText(strSpeech2Text);
                }
                break;
            default:
                break;
        }
    }

}
