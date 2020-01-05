package ec.edu.uce.optativa3.controlador;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;

import java.util.ArrayList;

import ec.edu.uce.optativa3.modelo.Log;
import ec.edu.uce.optativa3.modelo.daoLog;

public class mostrarLog extends AppCompatActivity {

    ListView lv;
    ArrayList<Log> lista;

    ArrayAdapter adaptador ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_log);

        lv= (ListView) findViewById(R.id.listaLog);

        daoLog DB = new daoLog(getApplicationContext(),null,null,1);
        lista=DB.llenarLista();
        adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        lv.setAdapter(adaptador);
    }
}
