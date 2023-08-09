package webjava.entidadesdenegocio;

import java.util.ArrayList;

public class rol {
    private int id;
    private String nombre;
    private int top_aux;
    private ArrayList<rol> rol;
   
    //Contructor
    public rol() {
    }

    public rol(int id, String nombre, int top_aux, ArrayList<rol> rol) {
        this.id = id;
        this.nombre = nombre;
        this.top_aux = top_aux;
        this.rol = rol;
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

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<rol> getRol() {
        return rol;
    }

    public void setRol(ArrayList<rol> rol) {
        this.rol = rol;
    }
}
