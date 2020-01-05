package ec.edu.uce.optativa3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
import com.example.login.R;

import ec.edu.uce.optativa3.modelo.Usuario;
import ec.edu.uce.optativa3.modelo.daoUsuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static String fecha1;
    static String getUsuario;
    EditText user, pass;
    Button btnEntrar, btnRegistrar;
    daoUsuario dao;
    TextView tvMensaje;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        permisoParaEscribirArchivos();

        user = (EditText) findViewById(R.id.User);
        pass = (EditText) findViewById(R.id.Pass);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnEntrar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        dao = new daoUsuario(this);

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
                if (u.equals("") && p.equals("")) {
                    Toast.makeText(this, "Error : Campos Vacios", Toast.LENGTH_LONG).show();
                } else if (dao.login(u, p) == 1) {

                    fecha1=fechaIn();

                    getUsuario=u;

                    Usuario ux = dao.getUsuario(u, p);
                    Toast.makeText(this, "Datos Correctos" + "\n" + "Archivo de Preferencias Compartidas Creado", Toast.LENGTH_LONG).show();
                    Intent c = new Intent(MainActivity.this, Mostrar.class);
                    c.putExtra("id", ux.getId());
                    startActivity(c);

                    //Intent im = new Intent(MainActivity.this, Registrar.class);
                    //im.putExtra("id", ux.getId());
                    //startActivity(im);


                } else {
                    Toast.makeText(this, "Estudiante o Contraseña incorrectos", Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.btnRegistrar:
                Intent i = new Intent(MainActivity.this, RegistroUsuario.class);
                startActivity(i);
                break;

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

}
