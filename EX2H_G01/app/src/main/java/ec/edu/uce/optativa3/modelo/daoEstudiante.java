package ec.edu.uce.optativa3.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoEstudiante {
    Context c;
    Estudiante u;
    ArrayList<Estudiante> lista;
    SQLiteDatabase sql;
    String bd = "BDUsuarios";
    String tabla = "create table if not exists estudiante (id integer primary key autoincrement, usuario text , nombre text, ap text, cel text, mail text, nacimiento text, genero text, becado text)";


    public daoEstudiante(Context c) {
        this.c = c;
        sql = c.openOrCreateDatabase(bd, c.MODE_PRIVATE, null);
        sql.execSQL(tabla);
        u = new Estudiante();

    }

    public boolean insertarUsuario(Estudiante u) {

        if (buscar(u.getUsuario()) == 0) {
            ContentValues cv = new ContentValues();
            cv.put("usuario", u.getUsuario());
            cv.put("nombre", u.getNombre());
            cv.put("ap", u.getApellido());
            cv.put("cel", u.getCelular());
            cv.put("mail", u.getCorreo());
            cv.put("nacimiento", u.getFechaNacimiento());
            cv.put("genero", u.getGenero());
            cv.put("becado", u.getBecado());
            return (sql.insert("estudiante", null, cv) > 0);

        } else {
            return false;
        }

    }

    public int buscar(String u) {
        int x = 0;
        lista = selectUsuarios();
        for (Estudiante us : lista) {
            if (us.getUsuario().equals(u)) {
                x++;

            }

        }
        return x;
    }


    public boolean eliminarUsuario(String user) {

        return (sql.delete("estudiante", "usuario = '" + user + "'", null)) > 0;

    }

    public ArrayList<Estudiante> selectUsuarios() {
        ArrayList<Estudiante> lista = new ArrayList<Estudiante>();
        lista.clear();
        Cursor cr = sql.rawQuery("select * from estudiante", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                Estudiante u = new Estudiante();
                u.setId(cr.getInt(0));
                u.setUsuario(cr.getString(1));
                u.setNombre(cr.getString(2));
                u.setApellido(cr.getString(3));
                u.setCelular(cr.getString(4));
                u.setCorreo(cr.getString(5));
                u.setFechaNacimiento(cr.getString(6));
                u.setGenero(cr.getString(7));
                u.setBecado(cr.getString(8));

                lista.add(u);
            } while (cr.moveToNext());

        }
        return lista;
    }

    public int login(String u, String p) {
        int a = 0;
        Cursor cr = sql.rawQuery("select * from estudiante", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                if (cr.getString(1).equals(u) && cr.getString(2).equals(p)) {

                    a++;
                }
            } while (cr.moveToNext());

        }
        return a;
    }


    public Estudiante getUsuarioById(int id) {
        lista = selectUsuarios();
        for (Estudiante us : lista) {
            if (us.getId() == id) {
                return us;
            }

        }
        return null;
    }


    public Estudiante getUsuarioByUser(String user) {
        lista = selectUsuarios();
        for (Estudiante us : lista) {
            if (us.getUsuario() == user) {
                return us;
            }

        }
        return null;
    }

    public boolean actualizarUsuario(Estudiante u) {

        ContentValues cv = new ContentValues();
        cv.put("nombre", u.getNombre());
        cv.put("ap", u.getApellido());
        cv.put("cel", u.getCelular());
        cv.put("mail", u.getCorreo());
        return (sql.update("estudiante",cv, "id = " + u.getId(), null) > 0);

    }

    public String estadoBecado (String user) {
        lista = selectUsuarios();

        String x="";

        for (Estudiante us : lista) {

            System.out.println("----IMPRIMIR ESTADO ESTUDIANTE: " + us.getBecado());

            x = us.getBecado();

        }


        return x;
    }
}
