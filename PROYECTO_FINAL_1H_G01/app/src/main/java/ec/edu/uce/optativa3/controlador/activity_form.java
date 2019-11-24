package ec.edu.uce.optativa3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


import com.example.login.R;


import ec.edu.uce.optativa3.modelo.Usuario;
import ec.edu.uce.optativa3.utilities.daoUsuario;

public class activity_form extends AppCompatActivity implements View.OnClickListener {

    private EditText txtUser;
    private EditText txtMail;
    private EditText txtCelular;
    private EditText txtContrasena;

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

        txtUser = findViewById(R.id.txt_user);
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

                Usuario u = new Usuario();
                u.setUsuario(txtUser.getText().toString());
                u.setContrasena(txtContrasena.getText().toString());
                u.setCelular(txtCelular.getText().toString());
                u.setCorreo(txtMail.getText().toString());

                if (!u.isNull()) {
                    Toast.makeText(this, "Error:campos vacios", Toast.LENGTH_LONG).show();

                } else if (dao.insertarUsuario(u)) {
                    Toast.makeText(this, "Registro Exitoso !!", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(activity_form.this, Mostrar.class);
                    startActivity(i2);
                    finish();

                } else {
                    Toast.makeText(this, "Usuario Ya Registrado!!!", Toast.LENGTH_LONG).show();
                }
                break;

        }

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
