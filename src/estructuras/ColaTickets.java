package estructuras;

import modelo.Ticket;
import modelo.EstadoTicket;
import java.util.LinkedList;
import java.util.Queue;

public class ColaTickets {
    private Queue<Ticket> cola;
    private int contadorTickets = 0;

    public ColaTickets() {
        cola = new LinkedList<>();
    }

    public void agregarTicket(Ticket ticket) {
        if(ticket.getId() ==0){
            ticket.setId(++contadorTickets);
        }
        cola.offer(ticket);
    }

    public Ticket atenderTicket() {
        return cola.poll();
    }

    // Metodo para reingresar un ticket al final
    public void reingresarTicket(Ticket ticket) {
        ticket.setEstado(EstadoTicket.EN_COLA);
        cola.offer(ticket);
        System.out.println("Ticket #" + ticket.getId() + " reingresado al final de la cola.");
    }

    //Verifica si exiten datos en la cola
    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public Ticket verSiguiente() {
        return cola.peek();
    }

    //Presenta todos los tickets
    public void mostrarCola() {
        if (cola.isEmpty()) {
            System.out.println("No hay casos en espera.");
            return;
        }
        System.out.println("\nCasos pendientes:");
        for (Ticket t : cola) {
            System.out.println("- " + t);
        }
    }

    //Presenta el historial completo
    public void mostrarHistorialTickets() {
        if (cola.isEmpty()) {
            System.out.println("No existen tickets registrados");
            return;
        }

        for (Ticket t : cola) {
            t.mostrarHistorial();
            System.out.println("------------------------------");
        }
    }
}