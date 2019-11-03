package com.example.login;


public class Usuario {

    private String fotoPerfilURL;
    private String nombre;
    private String apellido;
    private String usuario;
    private String contrasena;
    private String correo;
    private String celular;
    private long fechaNacimiento;
    private String genero;
    private boolean becado;

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

    public long getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(long fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isBecado() {
        return becado;
    }

    public void setBecado(boolean becado) {
        this.becado = becado;
    }

    public Usuario(String fotoPerfilURL, String nombre, String apellido, String usuario, String contrasena, String correo, String celular, long fechaNacimiento, String genero, boolean becado) {
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

    public Usuario(){

    }

    public boolean isNull(){

        if (nombre.equals("")&&apellido.equals("")&&usuario.equals("")&&contrasena.equals("")&&correo.equals("")&&celular.equals("")){

            return false;
        }else{
            return true;
        }

    }

    @Override
    public String toString() {
        return "Usuario{" +
                "fotoPerfilURL='" + fotoPerfilURL + '\'' +
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
}
