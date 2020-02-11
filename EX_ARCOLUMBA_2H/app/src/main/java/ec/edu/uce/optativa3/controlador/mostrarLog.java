package ec.edu.uce.optativa3.controlador;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ec.edu.uce.optativa3.modelo.Log;
import ec.edu.uce.optativa3.modelo.daoLog;

public class mostrarLog extends AppCompatActivity {

    ListView lv;
    ArrayList<Log> lista;
    ArrayList<Log> lista2;

    ArrayAdapter adaptador ;
    Button enviar;
    Button recuperar;
    private DatabaseReference baseLogFirebase;// ...
    DatabaseReference reff;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_log);
        baseLogFirebase = FirebaseDatabase.getInstance().getReference("Log");

        lv= (ListView) findViewById(R.id.listaLog);
        enviar=(Button)findViewById(R.id.btnenviar);
        recuperar=(Button)findViewById(R.id.btnRecuperar);

        daoLog DB = new daoLog(getApplicationContext(),null,null,1);
        lista=DB.llenarLista();
        adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        lv.setAdapter(adaptador);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mandarfirebase();
            }
        });


        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reff=FirebaseDatabase.getInstance().getReference();


                    reff.child("Log").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                long numHijos = dataSnapshot.getChildrenCount();

                                for(int i=1 ; i<=numHijos;i++) {

                                    String nombre = dataSnapshot.child(String.valueOf(i)).child("nombre").getValue().toString();

                                    String modelo = dataSnapshot.child(String.valueOf(i)).child("modelo").getValue().toString();
                                    String version = dataSnapshot.child(String.valueOf(i)).child("version").getValue().toString();
                                    String fechaHoraSal = dataSnapshot.child(String.valueOf(i)).child("fechaSal").getValue().toString();
                                    String fechaHoraIn = dataSnapshot.child(String.valueOf(i)).child("fechaIn").getValue().toString();
                                    String usu = dataSnapshot.child(String.valueOf(i)).child("usuario").getValue().toString();

                                    Log l = new Log();
                                    l.setFechaIn(fechaHoraIn);
                                    l.setFechaSal(fechaHoraSal);
                                    l.setModelo(modelo);
                                    l.setNombre(nombre);
                                    l.setVersion(version);
                                    l.setUsuario(usu);

                                    daoLog db = new daoLog(getApplicationContext(), null, null, 1);
                                    String mensaje = db.Registrar(l);
                                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();

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

    public void Mandarfirebase() {

        daoLog DB = new daoLog(getApplicationContext(),null,null,1);
        lista2=DB.llenarLista();

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


    public void recupererFirebase(){


    }
}
