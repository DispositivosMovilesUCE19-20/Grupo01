package ec.edu.uce.optativa3.controlador;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import ec.edu.uce.optativa3.modelo.Log;
import ec.edu.uce.optativa3.modelo.daoLog;

// clase  que  se ejecuta en segundo plano

public class ServicioLog extends Service {
    MainActivity main;
    String nombre;
    String modelo;
    String version;
    String fechaHoraSal;
    String fechaHoraIn;
    String usu;

public void OnCreate(){

super.onCreate();

}

public int onStartCommand(Intent intent, int flags , int startId){

    main = new MainActivity();
    nombre = Build.MANUFACTURER;
    modelo = Build.MODEL;
    version = Build.VERSION.RELEASE;
    fechaHoraSal=main.fechaIn();
    fechaHoraIn=main.fecha1;
    usu = main.getUsuario;


    Log l = new Log();
    l.setFechaIn(fechaHoraIn);
    l.setFechaSal(fechaHoraSal);
    l.setModelo(modelo);
    l.setNombre(nombre);
    l.setVersion(version);
    l.setUsuario(usu);

    daoLog db = new daoLog(getApplicationContext(),null,null,1);
    String mensaje =db.Registrar(l);
    Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
    return START_STICKY;
}









    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
