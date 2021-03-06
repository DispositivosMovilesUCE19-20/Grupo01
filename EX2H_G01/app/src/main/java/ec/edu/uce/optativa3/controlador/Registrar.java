package ec.edu.uce.optativa3.controlador;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.login.R;

import ec.edu.uce.optativa3.modelo.Estudiante;
import ec.edu.uce.optativa3.modelo.Usuario;
import ec.edu.uce.optativa3.modelo.daoEstudiante;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import ec.edu.uce.optativa3.modelo.daoLog;
import ec.edu.uce.optativa3.modelo.daoUsuario;

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

    daoEstudiante dao;

    //atrbutos del servicio web
    TextView tvMensaje;
    private RequestQueue queue;
    ArrayList<Estudiante> lista2;
    private DatabaseReference baseLogFirebase;// ...

    Usuario u;

    private Button recuperar;
    DatabaseReference reff;

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
        txtFechaDeNacimiento = findViewById(R.id.txtFechaDeNacimiento);
        rdHombre = findViewById(R.id.rdHombre);
        rdMujer = findViewById(R.id.rdMujer);
        btnRegistrar = findViewById(R.id.btnRegistrar2);
        btnCancelar = findViewById(R.id.btnCancelar);

        switchB = findViewById(R.id.swBecado);

        btnRegistrar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        dao = new daoEstudiante(this);

        tvMensaje = (TextView) findViewById(R.id.txtMensaje);
        queue = Volley.newRequestQueue(this);
        baseLogFirebase = FirebaseDatabase.getInstance().getReference("Estudiante");

        recuperar=(Button)findViewById(R.id.bntRecuperarEstudiantes);

        int id = 0;
        Bundle b = getIntent().getExtras();
        id = b.getInt("id");
        daoUsuario daoUser = new daoUsuario(this);
        u = daoUser.getUsuarioById(id);

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



        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff=FirebaseDatabase.getInstance().getReference();


                reff.child("Estudiante").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            long numHijos = dataSnapshot.getChildrenCount();

                            for(int i=1 ; i<=numHijos;i++) {

                               // String nombre = dataSnapshot.child(String.valueOf(i)).child("nombre").getValue().toString();


                                String nombre=dataSnapshot.child(String.valueOf(i)).child("nombre").getValue().toString();
                                String apellido=dataSnapshot.child(String.valueOf(i)).child("apellido").getValue().toString();
                                String usuario=dataSnapshot.child(String.valueOf(i)).child("usuario").getValue().toString();
                                String correo=dataSnapshot.child(String.valueOf(i)).child("correo").getValue().toString();
                                String celular=dataSnapshot.child(String.valueOf(i)).child("celular").getValue().toString();
                                String fechaNacimiento=dataSnapshot.child(String.valueOf(i)).child("fechaNacimiento").getValue().toString();
                                String genero=dataSnapshot.child(String.valueOf(i)).child("genero").getValue().toString();
                                String becado=dataSnapshot.child(String.valueOf(i)).child("becado").getValue().toString();

                                Estudiante e = new Estudiante();
                                e.setUsuario(usuario);
                                e.setNombre(nombre);
                                e.setApellido(apellido);
                                e.setCelular(celular);
                                e.setCorreo(correo);
                                e.setFechaNacimiento(fechaNacimiento);
                                e.setGenero(genero);
                                e.setBecado(becado);


                                daoEstudiante db = new daoEstudiante(getApplicationContext());
                                if (db.insertarUsuario(e)){

                                    Toast.makeText(getApplicationContext(), "Se inserto en firebase", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "No se inserto en firebase", Toast.LENGTH_LONG).show();

                                }

                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "No se registro exitosamente", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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

                Estudiante e = new Estudiante();
                e.setUsuario(txtUsuario.getText().toString());
                e.setNombre(txtNombre.getText().toString());
                e.setApellido(txtApellido.getText().toString());
                e.setCelular(txtCelular.getText().toString());
                e.setCorreo(txtCorreo.getText().toString());
                e.setFechaNacimiento(fechaDeNacimientoTexto);
                e.setGenero(genero);
                e.setBecado(beca);






                if (fotoPerfilUri != null) {

                    e.setFotoPerfilURL("Estudiante \n" + "");

                } else {

                    e.setFotoPerfilURL("https://firebasestorage.googleapis.com/v0/b/fir-chat-1ab2c.appspot.com/o/foto_perfil%2Ffoto_por_defecto.png?alt=media&token=40eed45f-bd7f-4178-8e7c-092d7eb1fb64");

                }


                if (!e.isNull()) {
                    Toast.makeText(this, "Error:campos vacios", Toast.LENGTH_LONG).show();

                } else if (dao.insertarUsuario(e)) {

                    //// mandar estudiantes a firebase

                    MandarfirebaseEstudiantes();

                    /////////////
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM", Locale.getDefault());
                    Date date = new Date();


                    String sSubCadena = fechaDeNacimientoTexto.substring(3,5);
                    String fecha = dateFormat.format(date);
                    Log.e("fech1",fecha);
                    Log.e("fech2",sSubCadena);
                    if(fecha.equals(sSubCadena)){
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "lemubitNotify")
                                .setSmallIcon(R.drawable.ic_add_black_24dp)
                                .setContentTitle("My notification")
                                .setContentText("Hoy es tu cumpleanios")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                                .setAutoCancel(true);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                        notificationManager.notify(101, builder.build());

                    }

                    Toast.makeText(this, "Registro Exitoso !!", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(Registrar.this, Mostrar.class);
                    i2.putExtra("id", u.getId());
                    startActivity(i2);
                    finish();

                } else {
                    Toast.makeText(this, "Estudiante Ya Registrado!!!", Toast.LENGTH_LONG).show();
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


    public void MandarfirebaseEstudiantes() {

        daoEstudiante DB = new daoEstudiante(this);
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
}
