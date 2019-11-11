package com.example.login;


public class Usuario {

    private int id;
    private String fotoPerfilURL;
    private String nombre;
    private String apellido;
    private String usuario;
    private String contrasena;
    private String correo;
    private String celular;
    private String fechaNacimiento;
    private String genero;
    private String becado;


    public Usuario(){

    }

    public boolean isNull(){

        if (nombre.equals("")&&apellido.equals("")&&usuario.equals("")&&contrasena.equals("")&&correo.equals("")&&celular.equals("")){

            return false;
        }else{
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

    public Usuario(int id, String fotoPerfilURL, String nombre, String apellido, String usuario, String contrasena, String correo, String celular, String fechaNacimiento, String genero, String becado) {
        this.id = id;
        this.fotoPerfilURL = fotoPerfilURL;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.becado = becado;
    }




    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", fotoPerfilURL='" + fotoPerfilURL + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", correo='" + correo + '\'' +
                ", celular='" + celular + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", genero='" + genero + '\'' +
                ", becado=" + becado +
                '}';
    }

    public String toJsonString() {
        return " {\"id\":  " + id + ",\n" +
                "    \"Usuario\": \"" + usuario + "\",\n" +
                "    \"nombre\": \"" + nombre + "\",\n" +
                "    \"apellido\": \"" + apellido + "\",\n" +
                "    \"contrasena\": \"" + contrasena + "\",\n" +
                "    \"correo\": \"" + correo + "\",\n" +
                "    \"celular\": \"" + celular + "\",\n" +
                "    \"fechaNacimiento\": \"" + fechaNacimiento + "\",\n" +
                "    \"genero\": \"" + genero + "\",\n" +
                "    \"becado\": \"" + becado + "\"}";
    }
}
