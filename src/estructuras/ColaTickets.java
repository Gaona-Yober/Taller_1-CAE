package estructuras;

import modelo.Ticket;
import modelo.EstadoTicket;

public class ColaTickets {
    private Nodo<Ticket> frenteNormal;
    private Nodo<Ticket> finNormal;
    private Nodo<Ticket> frenteUrgente;
    private Nodo<Ticket> finUrgente;
    private int contadorTickets = 0;

    public ColaTickets() {
        frenteNormal = finNormal = null;
        frenteUrgente = finUrgente = null;
    }

    // Agregar ticket
    public void agregarTicket(Ticket ticket) {
        agregarTicket(ticket, false); // por defecto normal
    }

    public void agregarTicket(Ticket ticket, boolean urgente) {
        if (ticket.getId() == 0) {
            ticket.setId(++contadorTickets);
        }

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

    // Verificación
    public boolean estaVacia() {
        return frenteNormal == null && frenteUrgente == null;
    }

    public Ticket verSiguiente() {
        if (frenteUrgente != null) return frenteUrgente.dato;
        return frenteNormal != null ? frenteNormal.dato : null;
    }

    // Mostrar todo
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
        if (estaVacia()) {
            System.out.println("No existen tickets registrados.");
            return;
        }

        if (frenteUrgente != null) {
            Nodo<Ticket> actual = frenteUrgente;
            while (actual != null) {
                actual.dato.mostrarHistorial();
                System.out.println("------------------------------");
                actual = actual.siguiente;
            }
        }

        if (frenteNormal != null) {
            Nodo<Ticket> actual = frenteNormal;
            while (actual != null) {
                actual.dato.mostrarHistorial();
                System.out.println("------------------------------");
                actual = actual.siguiente;
            }
        }
    }
}
