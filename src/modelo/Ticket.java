package modelo;

import estructuras.ListaNotas;

public class Ticket {
    private static int contador = 1;
    private int id;
    private final String nombreEstudiante;
    private final String cedula;
    private final String tramite;
    private EstadoTicket estado;
    private final ListaNotas historialNotas;

    public Ticket(String nombreEstudiante, String cedula, String tramite) {
        this.id = contador++;
        this.nombreEstudiante = nombreEstudiante;
        this.cedula = cedula;
        this.tramite = tramite;
        this.estado = EstadoTicket.EN_COLA;
        this.historialNotas = new ListaNotas();
        agregarNota("Ticket creado y agregado a la cola.");
    }

    // === GETTERS ===
    public int getId() {
        return id;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public String getCedula() {
        return cedula;
    }

    public String getTramite() {
        return tramite;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public void setId(int i) {
        if (id != contador - 1) {  // Si ya se asignó en constructor
            System.out.println("⚠️ Advertencia: ID no se puede cambiar después de crear el ticket.");
        } else {
            this.id = i;
        }
    }

    public boolean setEstado(EstadoTicket estadoTicket) {
        if (this.estado == estadoTicket) {
            System.out.println("El ticket ya está en [" + estadoTicket + "]");
            return true;
        }

        if (!EstadoTicket.esTransicionValida(this.estado, estadoTicket)) {
            System.out.println("TRANSICIÓN INVÁLIDA: " + this.estado + " → " + estadoTicket);
            System.out.println("Transiciones válidas desde " + this.estado + ":");
            mostrarTransicionesValidas();
            return false;  //
        }

        EstadoTicket anterior = this.estado;
        this.estado = estadoTicket;
        agregarNota("Estado cambiado: " + anterior + " → " + estadoTicket);
        System.out.println("Estado cambiado exitosamente a: " + estadoTicket);
        return true;
    }

    public boolean cambiarEstado(EstadoTicket nuevo) {
        return setEstado(nuevo);  // Reusa el setter validado
    }

    private void mostrarTransicionesValidas() {
        System.out.print("   → ");
        switch (estado) {
            case EN_COLA -> System.out.println("EN_ATENCION");
            case EN_ATENCION -> System.out.println("COMPLETADO, PENDIENTE_DOCS");
            case PENDIENTE_DOCS -> System.out.println("EN_COLA");
            case COMPLETADO -> System.out.println("NINGUNA (estado final)");
        }
    }

    // === NOTAS ===
    public void agregarNota(String texto) {
        historialNotas.agregarNota("[" + estado + "] " + texto);
    }

    public void eliminarNota(String texto) {
        historialNotas.eliminarNota(texto);
    }

    public void mostrarHistorial() {
        System.out.println("\n📜 HISTORIAL DEL TICKET #" + id + ":");
        System.out.println("Estudiante: " + nombreEstudiante + " (" + cedula + ")");
        System.out.println("Trámite: " + tramite);
        System.out.println("Estado actual: " + estado);
        System.out.println("Notas registradas:");
        historialNotas.mostrarNotas();
    }

    @Override
    public String toString() {
        return String.format("Ticket #%d | %s (%s) - %s [%s]",
                id, nombreEstudiante, cedula, tramite, estado);
    }
}