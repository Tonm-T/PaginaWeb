package webjava.entidadesdenegocio;

public class categorias {
    private int id;
    private String nombre;
    private String clasificacion;
    private int top_aux;
    private categorias categorias;

    public categorias() {
    }

    public categorias(int id, String nombre, String clasificacion, int top_aux, categorias categorias) {
        this.id = id;
        this.nombre = nombre;
        this.clasificacion = clasificacion;
        this.top_aux = top_aux;
        this.categorias = categorias;
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

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public categorias getCategorias() {
        return categorias;
    }

    public void setCategorias(categorias categorias) {
        this.categorias = categorias;
    }
    
    
}
