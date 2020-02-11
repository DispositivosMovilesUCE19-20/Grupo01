package ec.edu.uce.optativa3.modelo;

public class Log {
    private int id;
    private String usuario;
    private String fechaIn ;
    private String fechaSal;
    private String nombre ;
    private String modelo ;
    private String version;
    public Log() {

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

    public String getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(String fechaIn) {
        this.fechaIn = fechaIn;
    }

    public String getFechaSal() {
        return fechaSal;
    }

    public void setFechaSal(String fechaSal) {
        this.fechaSal = fechaSal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Log(int id, String usuario, String fechaIn, String fechaSal, String nombre, String modelo, String version) {
        this.id = id;
        this.usuario = usuario;
        this.fechaIn = fechaIn;
        this.fechaSal = fechaSal;
        this.nombre = nombre;
        this.modelo = modelo;
        this.version = version;
    }


    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", fechaIn='" + fechaIn + '\'' +
                ", fechaSal='" + fechaSal + '\'' +
                ", nombre='" + nombre + '\'' +
                ", modelo='" + modelo + '\'' +
                ", version='" + version + '\'' +
                '}';
    }


    public String toJsonString() {
        return " {\"usuario\": \"" + usuario + "\",\n" +
                "    \"fechaentrada\": \"" + fechaIn + "\",\n" +
                "    \"fechasalida\": \"" + fechaSal + "\",\n" +
                "    \"nombre\": \"" + nombre + "\",\n" +
                "    \"modelo\": \"" + modelo + "\",\n" +
                "    \"tversion\": \"" + version + "\"}";
    }
}
