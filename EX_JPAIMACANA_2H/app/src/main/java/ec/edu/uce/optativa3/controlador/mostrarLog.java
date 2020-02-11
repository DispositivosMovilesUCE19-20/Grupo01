package ec.edu.uce.optativa3.controlador;

import android.icu.util.UniversalTimeScale;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ec.edu.uce.optativa3.modelo.Log;
import ec.edu.uce.optativa3.modelo.daoLog;

public class mostrarLog extends AppCompatActivity {

    ListView lv,lvprueba;
    TextView tvprueba;

    ArrayList<Log> lista;
    ArrayList<Log> lista2;
    ArrayList<Log> lista3;

    Log log;


    ArrayAdapter adaptador,adaptador2 ;

    Button enviar;
    Button consultar;

    private DatabaseReference baseLogFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_log);
        baseLogFirebase = FirebaseDatabase.getInstance().getReference("Log");

        lv= (ListView) findViewById(R.id.listaLog);
        //objeto de prueba de consulta
        lvprueba = (ListView)findViewById(R.id.listanterior);
        tvprueba = (TextView)findViewById(R.id.txtprueba);


        enviar=(Button)findViewById(R.id.btnenviar);
        consultar=(Button)findViewById(R.id.btnconsultar);

        daoLog DB = new daoLog(getApplicationContext(),null,null,1);
        lista=DB.llenarLista();
        adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        adaptador2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista3);


        lv.setAdapter(adaptador);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mandarfirebase();
            }
        });

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarBaseDatos();
            }
        });



    }

    public void Mandarfirebase() {

        daoLog DB = new daoLog(getApplicationContext(),null,null,1);
        lista2=DB.llenarLista();

        int ultimoLista = lista2.size()-1;
        if(!lista2.isEmpty()){
            String id = baseLogFirebase.push().getKey();
            for(int i=0;i<=ultimoLista;i++){
                baseLogFirebase.child("users").child(String.valueOf(i+1)).setValue(lista2.get(i));
            }
            Toast.makeText(this,"registrado exitosamente",Toast.LENGTH_LONG).show();

        }else {

            Toast.makeText(this,"No se registro exitosamente",Toast.LENGTH_LONG).show();
        }


    }

    public void consultarBaseDatos(){
        baseLogFirebase = FirebaseDatabase.getInstance().getReference();
        baseLogFirebase.child("Log").child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    long centinela = dataSnapshot.getChildrenCount();
                    for(int i=4; i<=centinela; i++){
                        String usuario = dataSnapshot.child("usuario").getValue().toString();
                        String fechaIn = dataSnapshot.child("fechaIn").getValue().toString();
                        String fechaSal = dataSnapshot.child("fechaSal").getValue().toString();
                        String nombre = dataSnapshot.child("nombre").getValue().toString();
                        String modelo = dataSnapshot.child("modelo").getValue().toString();
                        String version = dataSnapshot.child("version").getValue().toString();

                        log = new Log();
                        //log.setId(id);
                        log.setUsuario(usuario);
                        log.setFechaIn(fechaIn);
                        log.setFechaSal(fechaSal);
                        log.setNombre(nombre);
                        log.setModelo(modelo);
                        log.setVersion(version);

                        //lista3.add(log);
                        tvprueba.setText(fechaIn);
                        //adaptador2.notifyDataSetChanged();
                        //lvprueba.setAdapter(adaptador2);
                        daoLog DB = new daoLog(getApplicationContext(),null,null,1);
                        String mensaje = DB.Registrar(log);
                        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

