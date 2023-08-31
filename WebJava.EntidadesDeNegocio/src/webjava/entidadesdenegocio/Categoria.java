package webjava.entidadesdenegocio;

import java.util.ArrayList;

public class Categoria {
    private int id;
    private String nombre;
    private String clasificacion;
    private int top_aux;
    private ArrayList<Categoria> categoria;

    public Categoria() {
    }

    public Categoria(int id, String nombre, String clasificacion, int top_aux, ArrayList<Categoria> categoria) {
        this.id = id;
        this.nombre = nombre;
        this.clasificacion = clasificacion;
        this.top_aux = top_aux;
        this.categoria = categoria;
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

    public ArrayList<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategoria(ArrayList<Categoria> categoria) {
        this.categoria = categoria;
    }
}
