package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoUsuario {
Context c;
Usuario u;
ArrayList<Usuario> lista;
SQLiteDatabase sql;
String bd="BDUsuarios";
String tabla="create table if not exists usuario (id integer primary key autoincrement, usuario text , pass text, nombre text, ap text, cel text, mail text, nacimiento text, genero text, becado text)";

public daoUsuario(Context c){
    this.c = c;
    sql=c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
sql.execSQL(tabla);
u=new Usuario();

}

public boolean insertarUsuario(Usuario u){
    if(buscar(u.getUsuario())==0){
        ContentValues cv=new ContentValues();
        cv.put("usuario",u.getUsuario());
        cv.put("pass",u.getContrasena());
        cv.put("nombre",u.getNombre());
        cv.put("ap",u.getApellido());
        cv.put("cel",u.getCelular());
        cv.put("mail",u.getCorreo());
        cv.put("nacimiento",u.getFechaNacimiento());
        cv.put("genero", u.getGenero());
        cv.put("becado", u.getBecado());

        return (sql.insert("usuario",null,cv)>0);

    }else{
        return false;
    }

}
public int buscar(String u){
    int x=0;
    lista=selectUsuarios();
    for (Usuario us:lista){
        if(us.getUsuario().equals(u)){
            x++;

        }

    }
    return x;
}

public ArrayList<Usuario> selectUsuarios(){
    ArrayList<Usuario> lista=new ArrayList<Usuario>();
    lista.clear();
    Cursor cr=sql.rawQuery("select * from usuario",null);
    if(cr!=null&& cr.moveToFirst()){
        do{
            Usuario u=new Usuario();
            u.setId(cr.getInt(0));
            u.setUsuario(cr.getString(1));
            u.setContrasena(cr.getString(2));
            u.setNombre(cr.getString(3));
            u.setApellido(cr.getString(4));
            u.setCelular(cr.getString(5));
            u.setCorreo(cr.getString(6));
            u.setFechaNacimiento(cr.getString(7));
            u.setGenero(cr.getString(8));
            u.setBecado(cr.getString(9));


            lista.add(u);
        }while (cr.moveToNext());

    }
    return lista;
}

public int login(String u , String p){
    int a =0;
    Cursor cr=sql.rawQuery("select * from usuario",null);
    if(cr!=null&& cr.moveToFirst()){
        do{
           if(cr.getString(1).equals(u)&&cr.getString(2).equals(p)){

               a++;
           }
        }while (cr.moveToNext());

    }
    return a;
}
public Usuario getUsuario(String u , String p){
    lista=selectUsuarios();
    for(Usuario us:lista){
        if(us.getUsuario().equals(u)&&us.getContrasena().equals(p)){
            return us;
        }

    }
    return null;
}

    public Usuario getUsuarioById(int id){
        lista=selectUsuarios();
        for(Usuario us:lista){
            if(us.getId()==id){
                return us;
            }

        }
        return null;
    }

}
