package ec.edu.uce.optativa3.modelo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class daoLog extends SQLiteOpenHelper{
    public daoLog(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "baseLog", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseLog) {
        BaseLog.execSQL("create table if not exists logs (codigo INTEGER primary key autoincrement,usuario text, fechaEntrada text, fechaSalida text,nombre text, modelo text,version text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String Registrar(Log log){
 String mensaje= "";
  SQLiteDatabase database =this.getReadableDatabase();

        ContentValues contenedor =new ContentValues();
        contenedor.put("usuario",log.getUsuario());
        contenedor.put("fechaEntrada",log.getFechaIn());
        contenedor.put("fechaSalida",log.getFechaSal());
        contenedor.put("nombre",log.getNombre());
        contenedor.put("modelo",log.getModelo());
        contenedor.put("version",log.getVersion());


        try{
            database.insertOrThrow("logs",null,contenedor);
              mensaje="Insertado Correctamente";
        }catch(SQLException e){

            mensaje="No Insertado";

        }



 return mensaje;


    }
    public ArrayList<Log> llenarLista (){

        ArrayList<Log> lista = new ArrayList<Log>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM logs";
        Cursor registros=database.rawQuery(q,null);

            if (registros.moveToFirst()){

                do{
                    Log l = new Log();

                        l.setId(registros.getInt(0));
                        l.setUsuario(registros.getString(1));
                        l.setFechaIn(registros.getString(2));
                        l.setFechaSal(registros.getString(3));
                        l.setNombre(registros.getString(4));
                        l.setModelo(registros.getString(5));
                        l.setVersion(registros.getString(6));



                        lista.add(l);

                }while (registros.moveToNext());

            }
        return lista;

    }
}
