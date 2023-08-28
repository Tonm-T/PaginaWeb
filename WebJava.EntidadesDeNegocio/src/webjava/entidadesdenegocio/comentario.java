package webjava.entidadesdenegocio;

import java.util.ArrayList;


public class comentario {
    private int id;
    private String contenido;
    private int top_aux;
    private ArrayList<comentario> comentario;

    public comentario() {
    }

    public comentario(int id, String contenido, int top_aux, ArrayList<comentario> comentario) {
        this.id = id;
        this.contenido = contenido;
        this.top_aux = top_aux;
        this.comentario = comentario;
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

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<comentario> getComentarios() {
        return comentario;
    }

    public void setComentarios(ArrayList<comentario> comentario) {
        this.comentario = comentario;
    }
    
    
}
