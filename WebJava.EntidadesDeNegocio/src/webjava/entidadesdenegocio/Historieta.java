package webjava.entidadesdenegocio;


public class Historieta {
    private int id;
    private String autor;
    private int categoriasid;
    private String nombre;
    private String descripcion;
    private String precio;
    private String precioanterior;
    private String link;
    private String editorioal;
    private byte edicion;
    private String fechapublicacion;
    private byte cantidadpag;
    private String imagen;
    private int top_aux;
    private Historieta historietas;

    public Historieta() {
    }

    public Historieta(int id, String autor, int categoriasid, String nombre, String descripcion, String precio, String precioanterior, String link, String editorioal, byte edicion, String fechapublicacion, byte cantidadpag, String imagen, int top_aux, Historieta historietas) {
        this.id = id;
        this.autor = autor;
        this.categoriasid = categoriasid;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.precioanterior = precioanterior;
        this.link = link;
        this.editorioal = editorioal;
        this.edicion = edicion;
        this.fechapublicacion = fechapublicacion;
        this.cantidadpag = cantidadpag;
        this.imagen = imagen;
        this.top_aux = top_aux;
        this.historietas = historietas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getCategoriasid() {
        return categoriasid;
    }

    public void setCategoriasid(int categoriasid) {
        this.categoriasid = categoriasid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getPrecioanterior() {
        return precioanterior;
    }

    public void setPrecioanterior(String precioanterior) {
        this.precioanterior = precioanterior;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEditorioal() {
        return editorioal;
    }

    public void setEditorioal(String editorioal) {
        this.editorioal = editorioal;
    }

    public byte getEdicion() {
        return edicion;
    }

    public void setEdicion(byte edicion) {
        this.edicion = edicion;
    }

    public String getFechapublicacion() {
        return fechapublicacion;
    }

    public void setFechapublicacion(String fechapublicacion) {
        this.fechapublicacion = fechapublicacion;
    }

    public byte getCantidadpag() {
        return cantidadpag;
    }

    public void setCantidadpag(byte cantidadpag) {
        this.cantidadpag = cantidadpag;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public Historieta getHistorietas() {
        return historietas;
    }

    public void setHistorietas(Historieta historietas) {
        this.historietas = historietas;
    } 
}
