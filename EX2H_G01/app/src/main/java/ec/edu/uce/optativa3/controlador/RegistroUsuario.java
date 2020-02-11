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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistroUsuario extends AppCompatActivity implements View.OnClickListener {

    //capturo las variables del formulario
    private EditText txtUsuario, txtPassword;
    private Button ingresarPersona;

    private daoUsuario dao;
    ArrayList<Usuario> lista2;
    private DatabaseReference baseLogFirebase;// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        txtUsuario = (EditText)findViewById(R.id.txtUser);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        ingresarPersona = (Button)findViewById(R.id.btnInsertarUsuario);
        ingresarPersona.setOnClickListener(this);

        dao = new daoUsuario(this);
        baseLogFirebase = FirebaseDatabase.getInstance().getReference("Usuario");


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
                String passwd =txtPassword.getText().toString();

                if (!u.isNull()) {
                    Toast.makeText(this, "Error:campos vacios", Toast.LENGTH_LONG).show();
                }

                       // descomentar para validar contraseña

              //  else if (!validarContrasenia(passwd)){

               //     Toast.makeText(this, "Contraseña no cumple con los parametros !!", Toast.LENGTH_LONG).show();

                //    }

                 else if (dao.insertarUsuario(u)) {
                    MandarfirebaseUsuarios();
                    Toast.makeText(this, "Registro Exitoso !!", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(RegistroUsuario.this, MainActivity.class);



                    startActivity(i2);
                    finish();

                } else {
                    Toast.makeText(this, "Usuario Ya Registrado!!!", Toast.LENGTH_LONG).show();
                }
                break;
        }


    }


    public void MandarfirebaseUsuarios() {

        daoUsuario DB = new daoUsuario(this);
        lista2=DB.selectUsuarios();

        int ultimoLista =lista2.size()-1;
        if(!lista2.isEmpty()){
            String id = baseLogFirebase.push().getKey();
            for (int i=0 ; i<=ultimoLista;i++){

                baseLogFirebase.child(String.valueOf(i+1)).setValue(lista2.get(i));
            }

            Toast.makeText(this,"registrado exitosamente",Toast.LENGTH_LONG).show();

        }else {

            Toast.makeText(this,"No se registro exitosamente",Toast.LENGTH_LONG).show();
        }


    }

    public  boolean validarContrasenia (String password){
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!¡\"#$%&'()*+,-./:;=?@\\^_`{|}~])(?=\\S+$).{8,}";
        return password.matches(pattern);

    }
}
