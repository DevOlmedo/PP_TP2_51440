package hilos;

import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.actividades.Actividad;

public class EnvioTicketsThread extends Thread {

    // Atributos
    public EventoUniversitario evento;

    // Constructor que recibe el evento sobre el cual se enviarán los tickets
    public EnvioTicketsThread(EventoUniversitario evento) {
        this.evento = evento;
    }

    @Override
    public void run() {
        System.out.println("[Hilo-Envío] Iniciando proceso de envío de tickets en segundo plano...");
        for (Actividad act : evento.getActividades()) {
            // Recorre las inscripciones de cada actividad
            for (Inscripcion ins : act.getInscripciones()) {
                // Si la inscripción tiene un ticket emitido, procede al envío
                if (ins.getTicket() != null) {
                    ins.getTicket().enviarTicket();
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        System.err.println("[Hilo-Envío] Error en el envío: " + e.getMessage());
                    }
                }
            }
        }

        System.out.println("[Hilo-Envío] === Finalizó el envío de tickets ===");
    }
}