package modelo;

import estructuras.ListaNotas;

public class Ticket {
    private static int contador = 1;
    private int id;
    private String nombreEstudiante;
    private String cedula;
    private String tramite;
    private EstadoTicket estado;
    private ListaNotas historialNotas;

    public Ticket(String nombreEstudiante, String cedula, String tramite) {
        this.id = contador++;
        this.nombreEstudiante = nombreEstudiante;
        this.cedula = cedula;
        this.tramite = tramite;
        this.estado = EstadoTicket.EN_COLA;
        this.historialNotas = new ListaNotas();
    }

    //Metodos basicos
    public int getId() { return id; }
    public String getNombreEstudiante() { return nombreEstudiante; }
    public String getCedula() { return cedula; }
    public String getTramite() { return tramite; }
    public EstadoTicket getEstado() { return estado; }
    public void setEstado(EstadoTicket estado) { this.estado = estado; }

    //Metodo para manejar el historial de notas
    public void agregarNota(String texto) {
        historialNotas.agregarNota("[" + estado + "] " + texto);
    }

    public void eliminarNota(String texto) {
        historialNotas.eliminarNota(texto);
    }

    public void mostrarHistorial() {
        System.out.println("\nHistorial del Ticket #" + id + ":");
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