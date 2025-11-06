package servicio;

import estructuras.ColaTickets;
import estructuras.PilaAcciones;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ColaTickets cola = new ColaTickets();
        PilaAcciones acciones = new PilaAcciones();

        ControladorTiket controller = new ControladorTiket(cola, acciones, sc);

        int opcion;
        do {
            System.out.println("\n--- Sistema CAE - Consola Principal ---");
            System.out.println("1. Registrar nuevo ticket");
            System.out.println("2. Atender caso");
            System.out.println("3. Deshacer / Rehacer acción");
            System.out.println("4. Mostrar cola de tickets");
            System.out.println("5. Consultar historial de tickets");
            System.out.println("6. Mostrar tickets atendidos y exportar");
            System.out.println("0. Salir");

            opcion = Validacion.leerOpcionMenu(sc, 0, 6);

            switch (opcion) {
                case 1 -> controller.registrarTicket();
                case 2 -> controller.atenderTicket();
                case 3 -> controller.deshacerRehacer();
                case 4 -> cola.mostrarCola();
                case 5 -> cola.mostrarHistorialTickets();
                case 6 -> cola.mostrarHistorialAtendidos();
                case 0 -> System.out.println("Saliendo...");
            }
        } while (opcion != 0);

        sc.close();
    }
}
