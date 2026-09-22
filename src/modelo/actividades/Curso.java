package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

public class Curso extends Actividad implements Certificable {
    private int nivel;

    // Constructor
    public Curso(int id, String titulo, int cupoMaximo, int nivel) {
        super(id, titulo, cupoMaximo);
        this.nivel = nivel;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public double calcularCostoMateriales() {
        return this.nivel * 1500.0; // O la fórmula que prefieras
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "--------------------------------------------------\n" +
                "             CERTIFICADO DE ASISTENCIA            \n" +
                "Entidad Emisora: " + ENTIDAD_EMISORA + "\n" +
                "Se certifica a: " + estudiante.getNombre() + " (Legajo: " + estudiante.getLegajo() + ")\n" +
                "Por completar el Curso: " + getTitulo() + " (Nivel: " + nivel + ")\n" +
                "--------------------------------------------------";
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}