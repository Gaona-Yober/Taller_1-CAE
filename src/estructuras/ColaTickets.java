package estructuras;

import modelo.Ticket;
import modelo.EstadoTicket;

import java.nio.charset.StandardCharsets;


public class ColaTickets {
    private Nodo<Ticket> frenteHistorial;
    private Nodo<Ticket> finHistorial;
    private Nodo<Ticket> frenteNormal;
    private Nodo<Ticket> finNormal;
    private Nodo<Ticket> frenteUrgente;
    private Nodo<Ticket> finUrgente;

    public ColaTickets() {
        frenteNormal = finNormal = null;
        frenteUrgente = finUrgente = null;
    }

    // Agregar ticket
    public void agregarTicket(Ticket ticket) {
        agregarTicket(ticket, false); // por defecto normal
    }

    public void agregarTicket(Ticket ticket, boolean urgente) {

        Nodo<Ticket> nuevo = new Nodo<>(ticket);

        if (urgente) {
            if (frenteUrgente == null) {
                frenteUrgente = finUrgente = nuevo;
            } else {
                finUrgente.siguiente = nuevo;
                finUrgente = nuevo;
            }
            System.out.println("Ticket agregado a la COLA URGENTE: " + ticket);
        } else {
            if (frenteNormal == null) {
                frenteNormal = finNormal = nuevo;
            } else {
                finNormal.siguiente = nuevo;
                finNormal = nuevo;
            }
            System.out.println("Ticket agregado a la COLA NORMAL: " + ticket);
        }
    }

    public void eliminarTicketPorId(int id){
        frenteNormal = eliminarDeCola(frenteNormal, id);
        frenteUrgente = eliminarDeCola(frenteUrgente, id);
    }

    private Nodo<Ticket> eliminarDeCola(Nodo<Ticket> frente, int id){
        if (frente == null) return null;
        if (frente.dato.getId() == id) return frente.siguiente;

        Nodo<Ticket> actual = frente;
        while (actual.siguiente != null && actual.siguiente.dato.getId() != id) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
        }
        return frente;
    }

    // Atender ticket
    public Ticket atenderTicket() {
        if (estaVacia()) {
            System.out.println("No hay tickets para atender.");
            return null;
        }

        Ticket ticketAtendido;

        // Urgentes tienen prioridad
        if (frenteUrgente != null) {
            ticketAtendido = frenteUrgente.dato;
            frenteUrgente = frenteUrgente.siguiente;
            if (frenteUrgente == null) finUrgente = null;
            System.out.println("Atendiendo ticket URGENTE: " + ticketAtendido);
        } else {
            ticketAtendido = frenteNormal.dato;
            frenteNormal = frenteNormal.siguiente;
            if (frenteNormal == null) finNormal = null;
            System.out.println("Atendiendo ticket NORMAL: " + ticketAtendido);
        }

        ticketAtendido.setEstado(EstadoTicket.EN_ATENCION);
        return ticketAtendido;
    }

    // Reingresar ticket al final
    public void reingresarTicket(Ticket ticket, boolean urgente) {
        ticket.setEstado(EstadoTicket.EN_COLA);
        agregarTicket(ticket, urgente);
        System.out.println("Ticket #" + ticket.getId() + " reingresado (" + (urgente ? "urgente" : "normal") + ").");
    }

    public void registrarHistorial(Ticket ticket){
        if (ticket == null || ticket.getEstado() != EstadoTicket.COMPLETADO) return;

        Nodo<Ticket> nuevo = new Nodo<>(ticket);
        if(frenteHistorial == null){
            frenteHistorial = finHistorial = nuevo;
        } else {
            finHistorial.siguiente = nuevo;
            finHistorial = nuevo;
        }
    }

    // Verificación
    public boolean estaVacia() {
        return frenteNormal == null && frenteUrgente == null;
    }

    public Ticket verSiguiente() {
        if (frenteUrgente != null) return frenteUrgente.dato;
        return frenteNormal != null ? frenteNormal.dato : null;
    }

    // Mostrar
    public void mostrarCola() {
        if (estaVacia()) {
            System.out.println("No hay casos en espera.");
            return;
        }

        System.out.println("\nCasos pendientes (prioridad global):");

        if (frenteUrgente != null) {
            System.out.println("COLA URGENTE:");
            Nodo<Ticket> actual = frenteUrgente;
            while (actual != null) {
                System.out.println("   - " + actual.dato);
                actual = actual.siguiente;
            }
        }

        if (frenteNormal != null) {
            System.out.println("COLA NORMAL:");
            Nodo<Ticket> actual = frenteNormal;
            while (actual != null) {
                System.out.println("   - " + actual.dato);
                actual = actual.siguiente;
            }
        }
    }

    public void mostrarHistorialTickets() {
        if (frenteUrgente == null && frenteNormal == null) {
            System.out.println("No existen tickets registrados.");
            return;
        }

        System.out.println("--- Historial de tickets ---");

        if (frenteUrgente != null) {
            System.out.println("\nCola Urgente:");
            Nodo<Ticket> actual = frenteUrgente;
            while (actual != null) {
                actual.dato.mostrarHistorial();
                System.out.println("------------------------------");
                actual = actual.siguiente;
            }
        }

        if (frenteNormal != null) {
            System.out.println("\nCola Normal:");
            Nodo<Ticket> actual = frenteNormal;
            while (actual != null) {
                actual.dato.mostrarHistorial();
                System.out.println("------------------------------");
                actual = actual.siguiente;
            }
        }
    }

    public void mostrarHistorialAtendidos(){
        if(frenteHistorial == null){
            System.out.println("No hay tickets atendidos aún.");
            return;
        }
        System.out.println("\n Historial de casos atendidos");
        Nodo<Ticket> actual = frenteHistorial;
        while (actual != null){
            System.out.println("  - " + actual.dato);
            actual = actual.siguiente;
        }
    }

    // Verifica si existe el historial
    public boolean historialVacio(){
        return frenteHistorial == null;
    }

    //Exportar Historial de casos atendidos
    public void exportarHistorialCSV(String rutaArchivo){
        if(frenteHistorial == null){
            System.out.println("No existen tickets atendidos");
            return;
        }
        try {
            String carpetaUsuario = System.getProperty("user.home");
            java.io.File carpetaDestino = new java.io.File(carpetaUsuario, "Documents/CAE_Historial");
            if(!carpetaDestino.exists()) carpetaDestino.mkdirs();

            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String rutaFinal = (rutaArchivo == null || rutaArchivo.isBlank())
                    ? " Historial_Atendidos_" + timestamp + ".csv"
                    : rutaArchivo.replaceAll("\\.csv$" , "") + "_" + timestamp + ".csv";
        java.io.File archivo  = new java.io.File(carpetaDestino, rutaFinal);

        try (java.io.Writer writer = new java.io.BufferedWriter(
                new java.io.OutputStreamWriter(
                        new java.io.FileOutputStream(archivo),
                        StandardCharsets.UTF_8
                )
        )){
            writer.write("ID, Nombre, Cédula, Trámite, Estado, Número de notas\n");

            Nodo<Ticket> actual = frenteHistorial;
            while(actual != null) {
                Ticket t = actual.dato;
                writer.write(String.format("%d,%s,%s,%s,%s,%d\n",
                        t.getId(),
                        t.getNombreEstudiante(),
                        t.getCedula(),
                        t.getTramite(),
                        t.getEstado(),
                        t.getHistorialNotasCount()));
                actual = actual.siguiente;
                }
            }
            System.out.println("Historial guardado corretamente en: " + archivo.getAbsolutePath());

        } catch (Exception e){
            System.out.println("Error al exportar historial: " + e.getMessage());
        }
    }

    public void eliminarDeHistorial(Ticket ticket){
        if (frenteHistorial == null || ticket == null) return;
        if (frenteHistorial.dato.equals(ticket)) {
            frenteHistorial = frenteHistorial.siguiente;
            if (frenteHistorial == null) finHistorial = null;
            System.out.println("Ticket eliminado del historial: " + ticket);
            return;
        }

        Nodo<Ticket> actual = frenteHistorial;
        while (actual.siguiente != null && !actual.siguiente.dato.equals(ticket)) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
          System.out.println("Ticket eliminado del historial: " + ticket);
        }
    }

}
