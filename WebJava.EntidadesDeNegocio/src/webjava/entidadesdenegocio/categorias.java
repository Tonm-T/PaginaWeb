package webjava.entidadesdenegocio;

public class categorias {
    private int id;
    private String nombre;
    private String clasificacion;

    public categorias() {
    }

    public categorias(int id, String nombre, String clasificacion) {
        this.id = id;
        this.nombre = nombre;
        this.clasificacion = clasificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
