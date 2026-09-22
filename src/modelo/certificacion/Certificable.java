package modelo.certificacion;

import modelo.Estudiante;

public interface Certificable {
    String ENTIDAD_EMISORA = "Universidad Tecnológica Nacional";
    String generarCertificado(Estudiante estudiante);
}