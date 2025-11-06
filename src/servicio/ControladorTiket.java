package servicio;

import estructuras.ColaTickets;
import estructuras.PilaAcciones;
import modelo.EstadoTicket;
import modelo.Ticket;

import java.util.Scanner;

public class ControladorTiket {

    private final ColaTickets cola;
    private final PilaAcciones acciones;
    private final Scanner sc;

    public ControladorTiket(ColaTickets cola, PilaAcciones acciones, Scanner sc) {
        this.cola = cola;
        this.acciones = acciones;
        this.sc = sc;
    }

    public void registrarTicket() {
        System.out.println("\nRegistro de nuevo estudiante");

        String nombre = Validacion.leerNombre(sc, "Nombre del estudiante");
        String identificacion = Validacion.leerIdentificacion(sc);
        String tramite = Validacion.leerNombre(sc, "Trámite (certificado, constancia, etc.)");

        System.out.print("¿Es un caso urgente? (s/n): ");
        boolean urgente = sc.nextLine().trim().equalsIgnoreCase("s");

        Ticket nuevo = new Ticket(nombre, identificacion, tramite);
            acciones.ejecutarAccion(
                () -> cola.agregarTicket(nuevo, urgente),
                () -> cola.eliminarTicketPorId(nuevo.getId())
        );

        System.out.println("\n Ticket generado con éxito: \n " + nuevo);
    }

    public void atenderTicket() {
        Ticket atendido = cola.atenderTicket();

        if (atendido == null) {
            System.out.println("No hay casos por atender.\n");
            return;
        }

        System.out.println("\nAtendiendo: " + atendido);

        System.out.print("¿Desea agregar una nota durante la atención? (s/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.print("Ingrese nota: ");
            atendido.agregarNota(sc.nextLine());
        }

        System.out.print("¿El trámite se completó? (s/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("s")) {
            acciones.ejecutarAccion(
                    () -> {
                        atendido.setEstado(EstadoTicket.COMPLETADO);
                        atendido.agregarNota("Trámite completado con éxito.");
                        cola.registrarHistorial(atendido);
                    },
                    () -> {
                        cola.eliminarDeHistorial(atendido);
                        cola.reingresarTicket(atendido, false);
                    }
            );
        } else {
            atendido.setEstado(EstadoTicket.PENDIENTE_DOCS);
            System.out.print("¿Reingresar como urgente? (s/n): ");
            boolean urgente = sc.nextLine().trim().equalsIgnoreCase("s");

            acciones.ejecutarAccion(
                    () -> cola.reingresarTicket(atendido, urgente),
                    () -> cola.eliminarTicketPorId(atendido.getId())
            );
        }
    }

    public void deshacerRehacer() {
        System.out.println("\n1. Deshacer");
        System.out.println("2. Rehacer");
        System.out.print("Seleccione: ");

        int opcion = Integer.parseInt(sc.nextLine());
        if (opcion == 1) acciones.deshacer();
        else if (opcion == 2) acciones.rehacer();
        else System.out.println("Opción inválida.");
    }

}
