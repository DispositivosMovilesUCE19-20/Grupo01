package ec.edu.uce.optativa3.modelo;


public class Usuario {

    private int id;
    private String usuario;
    private String contrasena;


    public Usuario() {

    }

    public boolean isNull() {

        if (usuario.equals("") && contrasena.equals("")) {

            return false;
        } else {
            return true;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    public Usuario(int id, String usuario, String contrasena) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }

    public String toJsonString() {
        return " {\"id\":  " + id + ",\n" +
                "    \"Usuario\": \"" + usuario + "\",\n" +
                "    \"contrasena\": \"" + contrasena + "\",\n" +
                "\"}";
    }
}
