package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import java.io.OutputStreamWriter;
import android.widget.Toast;
import android.widget.EditText;
import java.io.File;
import java.io.FileOutputStream;

import android.widget.CheckBox;

public class guardarActivity extends AppCompatActivity implements  View.OnClickListener{

    Button btCerrar;

    EditText txtFichero;
    CheckBox opUbicacionFichero;

    private Button buttonRefrescar;
    private Button buttonCerrar;
    private Button buttonGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar);

        btCerrar = findViewById(R.id.btCerrar);

        //Asignamos a cada objeto visual creado a su
        //respectivo elemento de main.xml

        buttonRefrescar = (Button) findViewById(R.id.btRefrescar);
        buttonCerrar = (Button) findViewById(R.id.btCerrar);
        buttonGuardar = (Button) findViewById(R.id.btGuardar);
        txtFichero   = (EditText) findViewById(R.id.txtFichero);
        opUbicacionFichero = (CheckBox) findViewById(R.id.opUbicacionFichero);


        btCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt=new Intent(guardarActivity.this,Mostrar.class);

                startActivityForResult(cInt, 0);
            }
        });

        //Botón para guardar datos en fichero
        buttonGuardar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //si el usuario selecciona Guardar en Tarjeta SDCard
                if (opUbicacionFichero.isChecked())
                {
                    String estadoSDCard = Environment.getExternalStorageState();
                    boolean almacenamientoExternoDisponible = false;
                    boolean almacenamientoExternoEscribible = false;

                    if (Environment.MEDIA_MOUNTED.equals(estadoSDCard))
                    {
                        almacenamientoExternoDisponible =
                                almacenamientoExternoEscribible = true;
                    }
                    else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(estadoSDCard))
                    {
                        almacenamientoExternoDisponible = true;
                        almacenamientoExternoEscribible = false;
                    }
                    else
                    {
                        almacenamientoExternoDisponible =
                                almacenamientoExternoEscribible = false;
                    }

                    if (almacenamientoExternoEscribible == true &
                            almacenamientoExternoDisponible == true)
                    {
                        String carpetaSDCard =
                                Environment.getExternalStorageDirectory().getAbsolutePath();
                        File carpetaWifi = new File (carpetaSDCard +
                                File.separator + "Download" + File.separator + txtFichero.getText().toString());
                        try
                        {
                            //creamos las carpetas si no existen
                            File carpetaWifiDir = new File (carpetaSDCard);
                            carpetaWifiDir.mkdirs();

                            String texto;
                            texto = "//creamos las carpetas si no existen";
                            byte bTexto[] = texto.getBytes();
                            FileOutputStream ficSalida = new FileOutputStream(carpetaWifi);
                            ficSalida.write(bTexto);
                            ficSalida.close();
                            Toast.makeText(getApplicationContext(),
                                    "Datos Wifi guardados en fichero: " +
                                            carpetaWifi.toString(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception ex)
                        {
                            //error, mostrar mensaje
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + ex.getMessage() + " " + carpetaWifi.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                "Error: tarjeta SD no disponible o no escribible",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //para guardar en carpeta de aplicación
                    String rutaFichero = txtFichero.getText().toString();
                    try
                    {
                        OutputStreamWriter fichero =
                                new OutputStreamWriter(
                                        openFileOutput(rutaFichero,
                                                Context.MODE_WORLD_WRITEABLE));

                        fichero.write( "//para guardar en carpeta de aplicación");
                        fichero.flush();
                        fichero.close();
                        Toast.makeText(getApplicationContext(),
                                "Datos Wifi guardados en fichero: " +
                                        rutaFichero, Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception ex)
                    {
                        //error, mostrar mensaje
                        Toast.makeText(getApplicationContext(),
                                "Error: " + ex.getMessage() + " " + rutaFichero,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

}
