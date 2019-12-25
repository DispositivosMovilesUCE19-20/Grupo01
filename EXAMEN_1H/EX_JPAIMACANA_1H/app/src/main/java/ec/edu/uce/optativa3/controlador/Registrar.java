package ec.edu.uce.optativa3.controlador;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.login.R;

import ec.edu.uce.optativa3.modelo.daoUsuario;

import com.kbeanie.multipicker.api.CacheLocation;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import ec.edu.uce.optativa3.modelo.Usuario;

/**
 * Created by Freddy
 */

public class Registrar extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView fotoPerfil;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtUsuario;
    private EditText txtCorreo;
    private EditText txtCelular;
    private EditText txtContrasena;
    private EditText txtFechaDeNacimiento;
    private RadioButton rdHombre;
    private RadioButton rdMujer;
    private Button btnRegistrar;
    private Button btnCancelar;

    private ImagePicker imagePicker;
    private CameraImagePicker cameraPicker;

    private String pickerPath;
    private Uri fotoPerfilUri;
    private long fechaDeNacimiento;

    private String fechaDeNacimientoTexto;

    private Switch switchB;

    daoUsuario dao;

    //atrbutos del servicio web
    TextView tvMensaje;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);
        fotoPerfil = findViewById(R.id.fotoPerfil);
        txtNombre = findViewById(R.id.RegNombre);
        txtApellido = findViewById(R.id.RegApellido);
        txtUsuario = findViewById(R.id.RegUsuario);
        txtCorreo = findViewById(R.id.RegMail);
        txtCelular = findViewById(R.id.RegCelular);
        txtContrasena = findViewById(R.id.RegPass);
        txtFechaDeNacimiento = findViewById(R.id.txtFechaDeNacimiento);
        rdHombre = findViewById(R.id.rdHombre);
        rdMujer = findViewById(R.id.rdMujer);
        btnRegistrar = findViewById(R.id.btnRegistrar2);
        btnCancelar = findViewById(R.id.btnCancelar);

        switchB = findViewById(R.id.swBecado);

        btnRegistrar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        dao = new daoUsuario(this);

        tvMensaje = (TextView) findViewById(R.id.txtMensaje);
        queue = Volley.newRequestQueue(this);

        obtenerDatos();

        imagePicker = new ImagePicker(this);
        cameraPicker = new CameraImagePicker(this);

        cameraPicker.setCacheLocation(CacheLocation.EXTERNAL_STORAGE_APP_DIR);

        imagePicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                if (!list.isEmpty()) {
                    String path = list.get(0).getOriginalPath();
                    fotoPerfilUri = Uri.parse(path);
                    fotoPerfil.setImageURI(fotoPerfilUri);
                }
            }

            @Override
            public void onError(String s) {
                Toast.makeText(Registrar.this, "Error: " + s, Toast.LENGTH_SHORT).show();
            }
        });

        cameraPicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                String path = list.get(0).getOriginalPath();
                fotoPerfilUri = Uri.fromFile(new File(path));
                fotoPerfil.setImageURI(fotoPerfilUri);
            }

            @Override
            public void onError(String s) {
                Toast.makeText(Registrar.this, "Error: " + s, Toast.LENGTH_SHORT).show();
            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Registrar.this);
                dialog.setTitle("Foto de perfil");

                String[] items = {"Galeria", "Camara"};

                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                imagePicker.pickImage();
                                break;
                            case 1:
                                pickerPath = cameraPicker.pickImage();
                                break;
                        }
                    }
                });

                AlertDialog dialogConstruido = dialog.create();
                dialogConstruido.show();

            }
        });

        txtFechaDeNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(Registrar.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int mes, int dia) {
                        Calendar calendarResultado = Calendar.getInstance();
                        calendarResultado.set(Calendar.YEAR, year);
                        calendarResultado.set(Calendar.MONTH, mes);
                        calendarResultado.set(Calendar.DAY_OF_MONTH, dia);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date date = calendarResultado.getTime();
                        fechaDeNacimientoTexto = simpleDateFormat.format(date);
                        fechaDeNacimiento = date.getTime();
                        txtFechaDeNacimiento.setText(fechaDeNacimientoTexto);
                    }
                }, calendar.get(Calendar.YEAR) - 18, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Picker.PICK_IMAGE_DEVICE && resultCode == RESULT_OK) {
            imagePicker.submit(data);
        } else if (requestCode == Picker.PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {
            cameraPicker.reinitialize(pickerPath);
            cameraPicker.submit(data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // You have to save path in case your activity is killed.
        // In such a scenario, you will need to re-initialize the CameraImagePicker
        outState.putString("picker_path", pickerPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // After Activity recreate, you need to re-intialize these
        // two values to be able to re-intialize CameraImagePicker
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("picker_path")) {
                pickerPath = savedInstanceState.getString("picker_path");
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegistrar2:

                final String genero;

                if (rdHombre.isChecked()) {
                    genero = "Hombre";
                } else {
                    genero = "Mujer";
                }

                final String beca;


                if (switchB.isChecked()) {

                    beca = "Becado";

                } else {
                    beca = "No es Becado";
                }

                Usuario u = new Usuario();


                    //Toast.makeText(this, "Contrasena valida", Toast.LENGTH_SHORT).show();



                    u.setUsuario(txtUsuario.getText().toString());
                    u.setContrasena(txtContrasena.getText().toString());
                    u.setNombre(txtNombre.getText().toString());
                    u.setApellido(txtApellido.getText().toString());
                    u.setCelular(txtCelular.getText().toString());
                    u.setCorreo(txtCorreo.getText().toString());
                    u.setFechaNacimiento(fechaDeNacimientoTexto);
                    u.setGenero(genero);
                    u.setBecado(beca);


                if (fotoPerfilUri != null) {

                    u.setFotoPerfilURL("Usuario \n" + "");

                } else {

                    u.setFotoPerfilURL("https://firebasestorage.googleapis.com/v0/b/fir-chat-1ab2c.appspot.com/o/foto_perfil%2Ffoto_por_defecto.png?alt=media&token=40eed45f-bd7f-4178-8e7c-092d7eb1fb64");

                }


                if (!u.isNull()) {
                    Toast.makeText(this, "Error:campos vacios", Toast.LENGTH_LONG).show();

                } else if (dao.insertarUsuario(u)) {
                    Toast.makeText(this, "Registro Exitoso !!", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(Registrar.this, MainActivity.class);
                    startActivity(i2);
                    finish();

                } else {
                    Toast.makeText(this, "Usuario Ya Registrado!!!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnCancelar:
                Intent i = new Intent(Registrar.this, MainActivity.class);
                startActivity(i);
                finish();
                break;

        }


    }

    private void obtenerDatos() {
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

    public boolean validarPwd(String pwd){
        boolean rtn = true;
        int seguidos = 0;
        char ultimo = 0xFF;

        int minuscula = 0;
        int mayuscula = 0;
        int numero = 0;
        int especial = 0;
        boolean espacio = false;
        if(pwd.length() < 8 || pwd.length() > 16) return false; // tamaño
        for(int i=0;i<pwd.length(); i++){
            char c = pwd.charAt(i);
            if(c <= ' ' || c > '~' ){
                rtn = false; //Espacio o fuera de rango
                break;
            }
            if( (c > ' ' && c < '0') || (c >= ':' && c < 'A') || (c >= '[' && c < 'a') || (c >= '{' && c < 127) ){
                especial++;
            }
            if(c >= '0' && c < ':') numero++;
            if(c >= 'A' && c < '[') mayuscula++;
            if(c >= 'a' && c < '{') minuscula++;

            seguidos = (c==ultimo) ? seguidos + 1 : 0;
            if(seguidos >= 2){
                rtn = false; // 3 seguidos
                break;
            }
            ultimo = c;
        }
        rtn = rtn && especial > 0 && numero > 0 && minuscula > 0 && mayuscula > 0;
        return rtn;
    }
}
