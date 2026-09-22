import exepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.Sala;
import modelo.actividades.Actividad;
import modelo.certificacion.Certificable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ========================================================
        // CONFIGURACIÓN DEL EVENTO Y REGISTRO DE ALUMNOS
        // ========================================================
        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante("70101", "Facundo Morales"));
        estudiantes.add(new Estudiante("70102", "Guadalupe Fuertes"));
        estudiantes.add(new Estudiante("70103", "Joaquín Díaz"));

        EventoUniversitario evento = new EventoUniversitario("EVNT-01", "Semana de la Innovación", 20000, false);
        Sala sala = new Sala(1, "Aula Magna");
        evento.asignarSala(sala);

        evento.crearCharla(100, "Tendencias en IA", 50, "Dra. Bianchi");
        evento.crearTaller(200, "Despliegue con Docker", 1, true); // Cupo 1
        evento.crearCurso(300, "Arquitectura de Software", 30, 2);

        // ========================================================
        // PROCESAMIENTO DE INSCRIPCIONES
        // ========================================================
        System.out.println("==================================================");
        System.out.println("           PROCESO DE INSCRIPCIÓN                 ");
        System.out.println("==================================================");

        try {
            // Inscripciones válidas
            evento.getActividades().get(0).inscribir(estudiantes.get(0)); // Facundo a Charla
            evento.getActividades().get(0).inscribir(estudiantes.get(1)); // Guadalupe a Charla
            evento.getActividades().get(1).inscribir(estudiantes.get(1)); // Guadalupe a Taller
            evento.getActividades().get(2).inscribir(estudiantes.get(0)); // Facundo a Curso
            evento.getActividades().get(2).inscribir(estudiantes.get(2)); // Joaquín a Curso
            System.out.println("Inscripciones válidas registradas con éxito.");

            // Caso fallido controlado de negocio: supera cupo en el Taller
            System.out.println("\nIntentando inscripción excedente en Taller...");
            evento.getActividades().get(1).inscribir(estudiantes.get(2)); // Joaquín (supera cupo de 1)

        } catch (CupoExcedidoException e) {
            System.err.println("Error: " + e.getMessage());
        }

        // ========================================================
        // EMISIÓN DE CERTIFICADOS
        // ========================================================
        System.out.println("\n==================================================");
        System.out.println("             CERTIFICADOS EMITIDOS                ");
        System.out.println("==================================================");

        for (Actividad act : evento.getActividades()) {
            if (act instanceof Certificable certificable) {
                for (Inscripcion ins : act.getInscripciones()) {
                    System.out.println(certificable.generarCertificado(ins.getEstudiante()));
                }
            }
        }

        // ========================================================
        // RESUMEN DEL EVENTO Y RECORRIDO POLIMÓRFICO
        // ========================================================
        System.out.println("\n==================================================");
        System.out.println("             DATOS DEL EVENTO                     ");
        System.out.println("==================================================");
        evento.mostrarDatos();

        System.out.println("\n--- Recorrido Polimórfico de Actividades ---");
        for (Actividad act : evento.getActividades()) {
            act.mostrarIdentificacion();
        }

        System.out.println("\nTotalidad de eventos creados: " + EventoUniversitario.getCantidadEventos());

        // ========================================================
        // PERSISTENCIA: CASO EXITOSO (Serialización y Lectura)
        // ========================================================
        System.out.println("\n==================================================");
        System.out.println("      PERSISTENCIA DE DATOS: CASO EXITOSO         ");
        System.out.println("==================================================");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("evento.dat"))) {
            oos.writeObject(evento);
            System.out.println("Objeto evento guardado correctamente en 'evento.dat'.");
        } catch (IOException e) {
            System.err.println("Error al persistir: " + e.getMessage());
        }

        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream("evento.dat"))) {
            EventoUniversitario eventoRecuperado = (EventoUniversitario) ios.readObject();
            System.out.println("Lectura exitosa: se recuperó el evento '" + eventoRecuperado.getTitulo() + "'.");
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado -> " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Clase incompatible -> " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de lectura -> " + e.getMessage());
        } finally {
            System.out.println("Cierre del flujo exitoso.");
        }

        // ========================================================
        // PERSISTENCIA: CASO FALLIDO CONTROLADO
        // ========================================================
        System.out.println("\n==================================================");
        System.out.println("   PERSISTENCIA DE DATOS: CASO FALLIDO CONTROLADO ");
        System.out.println("==================================================");

        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream("eventito.dat"))) {
            EventoUniversitario eventoErroneo = (EventoUniversitario) ios.readObject();
            eventoErroneo.mostrarDatos();
        } catch (FileNotFoundException e) {
            System.err.println("Error: El archivo 'eventito.dat' no existe -> " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Clase no encontrada -> " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error: Fallo de E/S -> " + e.getMessage());
        } finally {
            System.out.println("Cierre del flujo fallido.");
        }
    }
}