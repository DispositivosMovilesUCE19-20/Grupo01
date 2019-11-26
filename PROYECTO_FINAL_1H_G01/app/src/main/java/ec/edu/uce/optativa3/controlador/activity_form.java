package ec.edu.uce.optativa3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.login.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ec.edu.uce.optativa3.modelo.Usuario;
import ec.edu.uce.optativa3.utilities.daoUsuario;

public class activity_form extends AppCompatActivity implements View.OnClickListener {

    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtMail;
    private EditText txtCelular;
    private EditText txtContrasena;
    private static RequestQueue queue;
    int id = 0;

    Usuario u = new Usuario();

    // Comentar dependiendo de con cual se trabaja
    private Switch wsBecado;

    private Button btCancelar;
    private Button btSave;

    private daoUsuario dao;
    Intent x;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        txtNombre = findViewById(R.id.txt_nombre);
        txtApellido = findViewById(R.id.txt_apellido);
        txtMail = findViewById(R.id.txt_mail);
        txtCelular = findViewById(R.id.txt_celular);
        txtContrasena = findViewById(R.id.txt_contasena);

        btCancelar = findViewById(R.id.btn_CancelarF);
        btSave = findViewById(R.id.btn_saveF);

        btCancelar.setOnClickListener(this);
        btSave.setOnClickListener(this);


        Bundle b = getIntent().getExtras();

        id = b.getInt("id");
        dao = new daoUsuario(this);

        u = dao.getUsuarioById(id);

        txtMail.setText(u.getCorreo());
        txtContrasena.setText(u.getContrasena());
        txtCelular.setText(u.getCelular());
        txtNombre.setText(u.getNombre());
        txtApellido.setText(u.getApellido());




    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_CancelarF:

                Intent i5 = new Intent(activity_form.this, Mostrar.class);
                i5.putExtra("id", u.getId());
                startActivity(i5);
                finish();

                break;
            case R.id.btn_saveF:

                u.setNombre(txtNombre.getText().toString());
                u.setApellido(txtApellido.getText().toString());
                u.setContrasena(txtContrasena.getText().toString());
                u.setCelular(txtCelular.getText().toString());
                u.setCorreo(txtMail.getText().toString());

                if (!u.isNull()) {
                    Toast.makeText(this, "Error:campos vacios", Toast.LENGTH_LONG).show();

                } else if (dao.actualizarUsuario(u)) {

                   // obtenerDatos("msg2");
                    Toast.makeText(this, "El Estudiante se ha editado correctamente", Toast.LENGTH_LONG).show();

                    Intent i6 = new Intent(activity_form.this, Mostrar.class);
                    i6.putExtra("id", u.getId());
                    startActivity(i6);


                   // Mostrar.obtenerDatos("msg2");



                } else {
                    Toast.makeText(this, "Se produjo un problema en la Edición/Eliminación", Toast.LENGTH_LONG).show();
                }
                break;

        }

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



}
