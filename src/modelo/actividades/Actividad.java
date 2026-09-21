package modelo.actividades;

import exepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public abstract class Actividad implements Serializable {

    // Atributos
    public static final int CUPO_MINIMO = 1;
    private int id;
    private String titulo;
    private int cupoMaximo;
    private List<Inscripcion> inscripciones;

    // Constructor (las subclases lo llamarán con super(...))
    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;
        this.inscripciones = new ArrayList<>();
    }

    // Métodos para inscribir con Manejo de Errores

    public Inscripcion inscribir (Estudiante estudiante) throws CupoExcedidoException {
        if (inscripciones.size() < cupoMaximo) {
            Inscripcion nuevaInscripcion = new Inscripcion(estudiante, "CONFIRMADA");
            inscripciones.add(nuevaInscripcion);
            return nuevaInscripcion;
        } else {
            throw new CupoExcedidoException("Cupo Excedido de Inscripciones (Máximo: " + cupoMaximo + ")");
        }
    }

    public void mostrarInscripciones() {
        System.out.println("  Inscriptos en " + titulo + " (" + inscripciones.size() + "/" + cupoMaximo + "):");
        for (Inscripcion ins : inscripciones) {
            System.out.println("    - " + ins.getEstudiante().getNombre() +
                    " | Legajo: " + ins.getEstudiante().getLegajo() +
                    " | Fecha: " + ins.getFecha() +
                    " | Estado: " + ins.getEstado());
        }
    }

    public final void mostrarIdentificacion() {
        System.out.println("Tipo: " + getTipo() + " | ID: " + id + " | Título: " + titulo + " | Cupo Máx: " + cupoMaximo);
    }

    // Métodos abstractos
    public abstract double calcularCostoMateriales();
    public abstract String getTipo();

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }
}