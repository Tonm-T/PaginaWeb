package webjava.entidadesdenegocio;

import java.util.Date;

public class blogs {
    private int id;
    private int categoriasid;
    private int cometariosid;
    private String autor;
    private String nombre;
    private String descripcion;
    private String contenido;
    private Date fechacreacion;
    private String imagendescripcion;
    private String imagencontenido;
    private int top_aux;
    private categoria categorias;
    private comentario comentario;

    public blogs() {
    }

    public blogs(int id, int categoriasid, int cometariosid, String autor, String nombre, String descripcion, String contenido, Date fechacreacion, String imagendescripcion, String imagencontenido, int top_aux, categoria categorias, comentario comentarios) {
        this.id = id;
        this.categoriasid = categoriasid;
        this.cometariosid = cometariosid;
        this.autor = autor;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.fechacreacion = fechacreacion;
        this.imagendescripcion = imagendescripcion;
        this.imagencontenido = imagencontenido;
        this.top_aux = top_aux;
        this.categorias = categorias;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoriasid() {
        return categoriasid;
    }

    public void setCategoriasid(int categoriasid) {
        this.categoriasid = categoriasid;
    }

    public int getCometariosid() {
        return cometariosid;
    }

    public void setCometariosid(int cometariosid) {
        this.cometariosid = cometariosid;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getImagendescripcion() {
        return imagendescripcion;
    }

    public void setImagendescripcion(String imagendescripcion) {
        this.imagendescripcion = imagendescripcion;
    }

    public String getImagencontenido() {
        return imagencontenido;
    }

    public void setImagencontenido(String imagencontenido) {
        this.imagencontenido = imagencontenido;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public categoria getCategorias() {
        return categorias;
    }

    public void setCategorias(categoria categorias) {
        this.categorias = categorias;
    }

    public comentario getComentarios() {
        return comentario;
    }

    public void setComentarios(comentario comentarios) {
        this.comentario = comentarios;
    }
}
