package webjava.entidadesdenegocio;


public class comentarios {
    private int id;
    private String contenido;

    public comentarios() {
    }

    public comentarios(int id, String contenido) {
        this.id = id;
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
