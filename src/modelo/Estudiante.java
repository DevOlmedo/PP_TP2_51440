package modelo;

import java.io.Serializable;

public class Estudiante implements Serializable {
    // Atributos
    private String legajo;
    private String nombre;

    // Constructor
    public Estudiante(String legajo, String nombre) {
        this.legajo = legajo;
        this.nombre = nombre;
    }

    // Getters
    public String getLegajo() {
        return legajo;
    }
    public String getNombre() {
        return nombre;
    }
}
