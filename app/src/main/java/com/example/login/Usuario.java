package com.example.login;

public class Usuario {
    int id;
    String Nombre,Apellido,Usuario,Password,Mail,Celular, genero;
    long fechaNacimiento;

    public Usuario(int id, String nombre, String apellido, String usuario, String password, String mail, String celular, String genero, long fechaNacimiento) {
        this.id = id;
        Nombre = nombre;
        Apellido = apellido;
        Usuario = usuario;
        Password = password;
        Mail = mail;
        Celular = celular;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public long getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(long fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isNull(){

        if (Nombre.equals("")&&Apellido.equals("")&&Usuario.equals("")&&Password.equals("")&&Mail.equals("")&&Celular.equals("")){

            return false;
        }else{
            return true;
        }

    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", Nombre='" + Nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                ", Usuario='" + Usuario + '\'' +
                ", Password='" + Password + '\'' +
                ", Mail='" + Mail + '\'' +
                ", Celular='" + Celular + '\'' +
                '}';
    }








}
