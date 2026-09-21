import exepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Sala;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Registro de estudiantes
        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante("70101", "Facundo Morales"));
        estudiantes.add(new Estudiante("70102", "Guadalupe Fuertes"));
        estudiantes.add(new Estudiante("70103", "Joaquín Díaz"));

        // Construccion de eventos
        EventoUniversitario evento = new EventoUniversitario("EVNT-01", "Semana de la Innovación", 20000, false);

        // Asignacion de Sala
        Sala sala = new Sala(1, "Aula Magna");
        evento.asignarSala(sala);

        // Creacion de actividades para cada evento del tipo Charla y/o Taller
        evento.crearCharla(100, "Tendencias en IA", 50, "Dra. Bianchi");
        evento.crearTaller(200, "Despliegue con Docker", 1, true);

        // Inscripcion de estudiantes en cada actividad con manejo de excepciones
        try {
            // Inscripciones a la Charla (índice 0)
            evento.getActividades().get(0).inscribir(estudiantes.get(0)); // Facundo
            evento.getActividades().get(0).inscribir(estudiantes.get(1)); // Guadalupe

            // Inscripciones al Taller (índice 1)
            evento.getActividades().get(1).inscribir(estudiantes.get(1)); // Guadalupe
            evento.getActividades().get(1).inscribir(estudiantes.get(2)); // Joaquín

            System.out.println("Inscripciones realizadas correctamente.");

        } catch (CupoExcedidoException e) {
            System.err.println("Error al procesar la inscripción: " + e.getMessage());
        }

        // Resumen de datos mostrando su identificación de forma polimórfica
        System.out.println("\n--- Resumen del Evento ---");
        evento.mostrarDatos();

        System.out.println("\n--- Recorrido Polimórfico de Actividades ---");
        for (modelo.actividades.Actividad act : evento.getActividades()) {
            act.mostrarIdentificacion();
        }

        // Total de eventos creados
        System.out.println("\n--- Total de Eventos Creados ---");
        System.out.println("Totalidad de eventos creados: " + EventoUniversitario.getCantidadEventos());

        // Persistencia y Serializacion
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("evento.dat"))) {
            oos.writeObject(evento); // <- ACA SE PERSISTE TODO
            System.out.println("\nEvento persistido correctamente en el archivo 'evento.dat'");
        } catch (IOException e) {
            System.err.println("Error al persistir el evento: " + e.getMessage());
        }

        //Recupero de datos (Deserealizacion) "CASO FALLIDO"
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream("eventito.dat"))) {
            EventoUniversitario eventoRecuperado = (EventoUniversitario) ios.readObject();
            System.out.println("\nEvento recuperado correctamente desde el archivo:");
            eventoRecuperado.mostrarDatos();

        } catch (FileNotFoundException e) {
            // Archivo inexistente
            System.err.println("Error de lectura: No se encontró el archivo especificado: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // Clase no encontrada
            System.err.println("Error de compatibilidad: No se encontró la clase del objeto: " + e.getMessage());
        } catch (IOException e) {
            // Error general
            System.err.println("Error de E/S al leer el archivo: " + e.getMessage());
        } finally {
            System.out.println("Proceso de deserialización finalizado.");
        }

        //Recupero de datos (Deserealizacion) "CASO EXITOSO"
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream("evento.dat"))) {
            EventoUniversitario eventoRecuperado = (EventoUniversitario) ios.readObject();
            System.out.println("\nEvento recuperado correctamente desde el archivo:");
            eventoRecuperado.mostrarDatos();

        } catch (FileNotFoundException e) {
            // Archivo inexistente
            System.err.println("Error de lectura: No se encontró el archivo especificado: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // Clase no encontrada
            System.err.println("Error de compatibilidad: No se encontró la clase del objeto: " + e.getMessage());
        } catch (IOException e) {
            // Error general
            System.err.println("Error de E/S al leer el archivo: " + e.getMessage());
        } finally {
            System.out.println("Proceso de deserialización finalizado.");
        }
    }
}