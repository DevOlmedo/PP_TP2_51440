package modelo;

import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Taller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario implements Serializable {

    // Atributos
    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantidadEventos = 0;
    private Sala sala;                   // Agregación
    private List<Actividad> actividades; // Composición

    // Constructor
    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        this.actividades = new ArrayList<>();
        cantidadEventos++;
    }


    // Método para calcular el costo estimado
    public double calcularCostoEstimado() {
        if (this.gratuito) {
            return 0.0;
        }

        double costoMaterialesTotal = 0.0;
        for (Actividad act : this.actividades) {
            costoMaterialesTotal += act.calcularCostoMateriales(); // Llamada polimórfica
        }

        return (this.costoBase + costoMaterialesTotal) * 1.21;
    }

    // Agregación: Recibe una sala ya existente y la vincula
    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    // Composición: Crea y agrega Charla o Taller
    public void crearCharla(int id, String titulo, int cupo, String disertante) {
        this.actividades.add(new Charla(id, titulo, cupo, disertante));
    }

    public void crearTaller(int id, String titulo, int cupo, boolean requiereNotebook) {
        this.actividades.add(new Taller(id, titulo, cupo, requiereNotebook));
    }
    public void crearCurso(int id, String titulo, int cupo, int nivel) {
        this.actividades.add(new modelo.actividades.Curso(id, titulo, cupo, nivel));
    }

    // Filtra las actividades retornando una lista del tipo exacto solicitado
    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> filtradas = new ArrayList<>();
        for (Actividad act : this.actividades) {
            if (tipo.isInstance(act)) {
                filtradas.add(tipo.cast(act));
            }
        }
        return filtradas;
    }

    // Calcula el costo de materiales total para cualquier lista de actividades
    public double calcularCostoMateriales(List<? extends Actividad> listaActividades) {
        double total = 0.0;
        for (Actividad act : listaActividades) {
            total += act.calcularCostoMateriales();
        }
        return total;
    }


    // Mostrar Datos integrado con Sala, Identificación polimórfica e Inscripciones
    public void mostrarDatos() {
        System.out.println("--------------------------------------------------");
        System.out.println("ID: " + id +
                " | Título: " + titulo +
                " | Costo Base: $" + costoBase +
                " | Costo Estimado (c/impuestos): $" + String.format("%.2f", calcularCostoEstimado()) +
                " | Gratuito: " + (gratuito ? "Sí" : "No"));

        System.out.println("Sala: " + (sala != null ? sala.getNombre() + " (ID: " + sala.getId() + ")" : "Sin sala asignada"));

        System.out.println("Actividades del Evento:");
        if (actividades.isEmpty()) {
            System.out.println("  (No hay actividades registradas)");
        } else {
            for (Actividad act : actividades) {
                System.out.print("  -> ");
                act.mostrarIdentificacion(); // Método final que llama a getTipo() polimórfico
                System.out.println("     Costo de materiales: $" + act.calcularCostoMateriales());
                act.mostrarInscripciones();
            }
        }
        System.out.println("--------------------------------------------------");
    }

    // Metodo contador
    public static int getCantidadEventos() {
        return cantidadEventos;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }

    public boolean isGratuito() {
        return gratuito;
    }

    public void setGratuito(boolean gratuito) {
        this.gratuito = gratuito;
    }

    public Sala getSala() {
        return sala;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }
}