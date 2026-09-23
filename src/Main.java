import exepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.Sala;
import modelo.actividades.Actividad;
import modelo.certificacion.Certificable;
import hilos.EnvioTicketsThread;

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
            //Uso sout porque sino el error queda desfasado al final de la ejecucion y no se entiende
        } catch (FileNotFoundException e) {
            System.out.println("Error: El archivo 'eventito.dat' no existe -> " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Clase no encontrada -> " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: Fallo de E/S -> " + e.getMessage());
        } finally {
            System.out.println("Cierre del flujo fallido.");
        }

        // ========================================================
        // FILTRADO DE ACTIVIDADES CON GENÉRICOS Y COSTOS DE MATERIALES
        // ========================================================
        System.out.println("\n==================================================");
        System.out.println("       FILTRADO DE ACTIVIDADES                      ");
        System.out.println("==================================================");

        List<modelo.actividades.Charla> charlas = evento.filtrarActividadesPorTipo(modelo.actividades.Charla.class);
        List<modelo.actividades.Taller> talleres = evento.filtrarActividadesPorTipo(modelo.actividades.Taller.class);
        List<modelo.actividades.Curso> cursos = evento.filtrarActividadesPorTipo(modelo.actividades.Curso.class);

        System.out.println("Cantidad de Charlas: " + charlas.size());
        System.out.println("Cantidad de Talleres: " + talleres.size());
        System.out.println("Cantidad de Cursos:   " + cursos.size());

        double costoCharlas = evento.calcularCostoMateriales(charlas);
        double costoTalleres = evento.calcularCostoMateriales(talleres);
        double costoCursos = evento.calcularCostoMateriales(cursos);
        double costoTotal = evento.calcularCostoMateriales(evento.getActividades());

        System.out.println("\nCosto de materiales Charlas:  $" + costoCharlas);
        System.out.println("Costo de materiales Talleres: $" + costoTalleres);
        System.out.println("Costo de materiales Cursos:   $" + costoCursos);
        System.out.println("Costo total de materiales:    $" + costoTotal);

        // ========================================================
        // TICKETS DE ACCESO Y CONCURRENCIA (HILOS)
        // ========================================================
        System.out.println("\n==================================================");
        System.out.println("   TICKETS DE ACCESO Y CONCURRENCIA                 ");
        System.out.println("==================================================");

        // Genera un ticket de acceso por cada inscripción confirmada
        System.out.println("[Main] Generando tickets para las inscripciones confirmadas...");
        for (Actividad act : evento.getActividades()) {
            for (Inscripcion ins : act.getInscripciones()) {
                ins.generarTicket();
            }
        }

        // Inicia el proceso concurrente en un hilo separado
        System.out.println("[Main] Lanzando el hilo de envío de tickets en segundo plano...\n");
        EnvioTicketsThread hiloEnvio = new EnvioTicketsThread(evento);
        hiloEnvio.start();
        System.out.println("[Main] El hilo de envío ya está en ejecución paralela.");
        System.out.println("[Main] Continuando con tareas en el hilo principal:");

        for (Actividad act : evento.getActividades()) {
            System.out.println("[Main] -> Consultando estado de: " + act.getTitulo() + " (Tipo: " + act.getTipo() + ")");
            // Simula Alternancia entre los 2 flujos:
            try {
                Thread.sleep(400);
            } catch (InterruptedException ignored) {}
        }

        // Esperamos a que el hilo secundario termine antes de cerrar el programa
        try {
            hiloEnvio.join();
        } catch (InterruptedException ignored) {}

        System.out.println("\n[Main] Todas las tareas e hilos concluyeron exitosamente.");

    }
}