package modelo;

import java.io.Serializable;

public class Sala implements Serializable {
    // Atributos
    private int id;
    private String nombre;

    // Constructor
    public Sala(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

}
