package webjava.entidadesdenegocio;

import java.util.ArrayList;

public class Rol {
    private int id;
    private String nombre;
    private int top_aux;
    private ArrayList<Rol> rol;

    public Rol() {
    }

    public Rol(int id, String nombre, int top_aux, ArrayList<Rol> rol) {
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

    public ArrayList<Rol> getRol() {
        return rol;
    }

    public void setRol(ArrayList<Rol> rol) {
        this.rol = rol;
    }
}
