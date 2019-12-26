package ec.edu.uce.optativa3.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.optativa3.modelo.Estudiante;
import ec.edu.uce.optativa3.modelo.Usuario;
import ec.edu.uce.optativa3.modelo.daoEstudiante;
import ec.edu.uce.optativa3.modelo.daoUsuario;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;

public class RegistroUsuario extends AppCompatActivity implements View.OnClickListener {

    //capturo las variables del formulario
    private EditText txtUsuario, txtPassword;
    private Button ingresarPersona;

    private daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        txtUsuario = (EditText)findViewById(R.id.txtUser);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        ingresarPersona = (Button)findViewById(R.id.btnInsertarUsuario);
        ingresarPersona.setOnClickListener(this);

        dao = new daoUsuario(this);

    }



    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInsertarUsuario:

                Usuario u = new Usuario();
                u.setUsuario(txtUsuario.getText().toString());
                u.setContrasena(txtPassword.getText().toString());

                if (!u.isNull()) {
                    Toast.makeText(this, "Error:campos vacios", Toast.LENGTH_LONG).show();

                } else if (dao.insertarUsuario(u)) {
                    Toast.makeText(this, "Registro Exitoso !!", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(RegistroUsuario.this, MainActivity.class);

                    /* Pruebas de ingreso
                    Estudiante e = new Estudiante();
                    daoEstudiante daoE = new daoEstudiante(this);

                    e.setUsuario("prueba");
                    e.setCorreo("prueba");
                    e.setCelular("prueba");
                    e.setBecado("prueba");

                    if (daoE.insertarUsuario(e)) {
                        Toast.makeText(this, "Estudiante Exitoso !!", Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(this, "Estudiante no registrado !!", Toast.LENGTH_LONG).show();
                    }
                    */

                    startActivity(i2);
                    finish();

                } else {
                    Toast.makeText(this, "Usuario Ya Registrado!!!", Toast.LENGTH_LONG).show();
                }
                break;
        }


    }
}
