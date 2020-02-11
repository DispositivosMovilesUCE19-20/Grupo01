package ec.edu.uce.optativa3.modelo;


public class Estudiante {

    private int id;
    private String fotoPerfilURL;
    private String nombre;
    private String apellido;
    private String usuario;
    private String correo;
    private String celular;
    private String fechaNacimiento;
    private String genero;
    private String becado;


    public Estudiante() {

    }

    public boolean isNull() {

        if (nombre.equals("") && apellido.equals("") && correo.equals("") && celular.equals("")) {

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

    public String getFotoPerfilURL() {
        return fotoPerfilURL;
    }

    public void setFotoPerfilURL(String fotoPerfilURL) {
        this.fotoPerfilURL = fotoPerfilURL;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getBecado() {
        return becado;
    }

    public void setBecado(String becado) {
        this.becado = becado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Estudiante(int id, String fotoPerfilURL, String nombre, String apellido, String correo, String celular, String fechaNacimiento, String genero, String becado) {
        this.id = id;
        this.fotoPerfilURL = fotoPerfilURL;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.becado = becado;
    }


    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", fotoPerfilURL='" + fotoPerfilURL + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", celular='" + celular + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", genero='" + genero + '\'' +
                ", becado=" + becado +
                '}';
    }

    public String toJsonString() {
        return " {\"id\":  " + id + ",\n" +
                "    \"nombre\": \"" + nombre + "\",\n" +
                "    \"apellido\": \"" + apellido + "\",\n" +
                "    \"correo\": \"" + correo + "\",\n" +
                "    \"celular\": \"" + celular + "\",\n" +
                "    \"fechaNacimiento\": \"" + fechaNacimiento + "\",\n" +
                "    \"genero\": \"" + genero + "\",\n" +
                "    \"becado\": \"" + becado + "\"}";
    }
}
