# Trabajo Práctico N° 2 - Programación Orientada a Objetos en Java

* **Materia:** Paradigmas de Programación
* **Institución:** UTN - Facultad Regional Mendoza (FRM)
* **Legajo:** 51440
---

## 📌 Descripción del Proyecto

Este proyecto escala el sistema de administración de eventos universitarios desarrollado en el TP N°1, incorporando organización en *packages*, manejo de excepciones, persistencia de objetos, interfaces, *generics* con *wildcards*, clases anidadas y concurrencia mediante hilos:

* **Organización en packages:** Separación del modelo en `modelo`, `excepciones`, `certificacion` e `hilos` para mejorar el encapsulamiento y la mantenibilidad del código.
* **Manejo de excepciones:** Implementación de `CupoExcedidoException` (excepción chequeada) para controlar cupos agotados e inscripciones que no alcanzan el cupo mínimo, junto con bloques `try-catch-finally` para gestionar fallos de forma granular.
* **Persistencia de objetos:** Serialización y deserialización de eventos universitarios para guardar y recuperar el estado del sistema desde archivo.
* **Interfaces:** Incorporación de la interfaz `Certificable`, implementada por `Taller` y `Curso` (no por `Charla`), para la emisión de certificados de asistencia.
* **Generics y wildcards:** Métodos parametrizados acotados y *wildcards* para filtrar actividades por tipo y calcular el costo de materiales sobre listas de actividades y sus subtipos.
* **Clases anidadas e hilos:** Clase anidada `Inscripcion.TicketDeAcceso` para modelar tickets en el contexto de una inscripción confirmada, y la clase `EnvioTicketsThread` para el envío concurrente de tickets sin interrumpir la ejecución del programa principal.
---

## 🚀 Estructura del Código

* `src/`
    * `Main.java`: Clase ejecutable que demuestra ordenadamente los requerimientos de los ejercicios 1, 2, 3 y 4.
    * `expeciones/`
        * `CupoExcedidoException.java`: Excepción chequeada lanzada al superar el cupo máximo de una actividad.
    * `hilos/`
        * `EnvioTicketsThread.java`: Clase pública que extiende `Thread` y ejecuta en un hilo separado el envío de todos los tickets generados para las inscripciones confirmadas de un evento.
    * `modelo/`
        * `EventoUniversitario.java`: Entidad principal con control de cupos, salas, persistencia (`persistirEvento()` / `recuperarEvento()`) y los métodos genéricos `filtrarActividadesPorTipo(Class<T>)` y `calcularCostoMateriales(List<? extends Actividad>)`.
        * `Sala.java`: Representación de espacios físicos independientes.
        * `Estudiante.java`: Registro de participantes.
        * `Inscripcion.java`: Clase asociativa que registra fecha y estado de la inscripción, y contiene la clase anidada `TicketDeAcceso`.
        * `actividades/`
            * `Actividad.java`: Clase base abstracta.
            * `Charla.java`: Subclase con costo de materiales gratuito ($0), no certificable.
            * `Taller.java`: Subclase con costo condicional según requerimiento de notebook ($5000 o $2000), implementa `Certificable`.
            * `Curso.java`: Nueva subclase incorporada, implementa `Certificable`.
        * `certificacion/`
            * `Certificable.java`: Interfaz con el método `generarCertificado(Estudiante)`, implementada por las actividades certificables (`Taller`, `Curso`).
  * `assets/`
      * `Imágenes de la Ejecución`: 3 imágenes que evidencian la resolución del Trabajo Práctico (`salida_consola(1)`, `salida_consola(2)`,`salida_consola(3)`).
---

## 🧩 Detalle por Ejercicio

| Ejercicio | Contenido |
|---|---|
| **1** | Modularización en *packages*, `CupoExcedidoException`, manejo de excepciones y persistencia por serialización/deserialización. |
| **2** | Interfaz `Certificable`, nueva actividad `Curso` y emisión de certificados para talleres y cursos (no charlas). |
| **3** | Métodos genéricos acotados (`filtrarActividadesPorTipo`) y *wildcards* (`calcularCostoMateriales`) sobre las actividades del evento. |
| **4** | Clase anidada `Inscripcion.TicketDeAcceso` y envío concurrente de tickets mediante `EnvioTicketsThread`. |
 
---

## 💻 Instrucciones de Ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/DevOlmedo/PP_TP2_51440.git
```

### 2. Abrir en el IDE

1. Abrir IntelliJ IDEA.
2. Seleccionar **File > Open...** y elegir la carpeta del proyecto clonado.
### 3. Configuración del JDK

Asegurarse de tener seleccionado un JDK (versión 17 o superior) en:

**File > Project Structure > Project SDK**

### 4. Ejecutar el programa

1. Ir a la ruta `src/App.java`.
2. Hacer clic derecho sobre el archivo.
3. Seleccionar **Run 'App.main()'**.
---

## 📷 Salida por Consola
![Salida Consola Parte 1](src/assets/salida_consola(1).png)
![Salida Consola Parte 2](src/assets/salida_consola(2).png)
![Salida Consola Parte 3](src/assets/salida_consola(3).png)
