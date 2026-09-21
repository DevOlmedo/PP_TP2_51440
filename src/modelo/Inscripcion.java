package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {
    // Atributos
    private LocalDate fecha;
    private String estado;
    private Estudiante estudiante; // Vínculo con el estudiante

    // Constructor
    public Inscripcion(Estudiante estudiante, String estado) {
        this.fecha = LocalDate.now();
        this.estado = estado;
        this.estudiante = estudiante;
    }

    // Getters
    public LocalDate getFecha() {
        return fecha;
    }
    public String getEstado() {
        return estado;
    }
    public Estudiante getEstudiante() {
        return estudiante;
    }
}