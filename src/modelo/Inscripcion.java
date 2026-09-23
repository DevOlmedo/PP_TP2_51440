package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {

    // Atributos
    private LocalDate fecha;
    private String estado;
    private Estudiante estudiante; // Vínculo con el estudiante
    private TicketDeAcceso ticket; // Atributo que almacena el ticket de acceso[cite: 7]

    // Constructor
    public Inscripcion(Estudiante estudiante, String estado) {
        this.fecha = LocalDate.now();
        this.estado = estado;
        this.estudiante = estudiante;
        this.ticket = null; // Inicialmente sin ticket
    }

    // Emite el ticket únicamente si la inscripción está confirmada
    public boolean generarTicket() {
        if ("CONFIRMADA".equalsIgnoreCase(this.estado)) {
            // Generamos un identificador único para el ticket
            String idGenerado = "TCK-" + estudiante.getLegajo() + "-" + (System.currentTimeMillis() % 10000);
            this.ticket = new TicketDeAcceso(idGenerado);
            return true;
        } else {
            System.out.println("No se pudo emitir ticket: La inscripción de " + estudiante.getNombre() + " no está confirmada.");
            return false;
        }
    }

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public TicketDeAcceso getTicket() {
        return ticket;
    }

    // Clase anidada

    public class TicketDeAcceso implements Serializable {
        private String idTicket;
        private LocalDate fechaEmision;

        public TicketDeAcceso(String idTicket) {
            this.idTicket = idTicket;
            this.fechaEmision = LocalDate.now();
        }

        public void enviarTicket() {
            System.out.println("[Hilo-Envío] -> Enviando ticket " + idTicket +
                    " a " + estudiante.getNombre() + " (Legajo: " + estudiante.getLegajo() +
                    ") - Fecha de emisión: " + fechaEmision);
        }

        // Getters
        public String getIdTicket() {
            return idTicket;
        }

        public LocalDate getFechaEmision() {
            return fechaEmision;
        }
    }
}