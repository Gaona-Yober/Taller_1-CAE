package servicio;

import estructuras.ColaTickets;
import estructuras.PilaAcciones;
import modelo.Ticket;
import modelo.EstadoTicket;

import java.util.Scanner;

public class MainApp {

    // Método para leer los nombres (solo letras y espacios)
    private static String leerNombre(Scanner sc, String campo) {
        String valor;
        do {
            System.out.print(campo + ": ");
            valor = sc.nextLine().trim();
            if (!valor.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
                System.out.println("Ingrese solo letras y espacios. Intente de nuevo.");
                valor = null;
            }
        } while (valor == null || valor.isEmpty());
        return valor;
    }

    // Método de validación de cédula (solo números y 10 dígitos)
    private static String leerCedula(Scanner sc) {
        String valor;
        do {
            System.out.print("Cédula (10 dígitos): ");
            valor = sc.nextLine().trim();
            if (!valor.matches("\\d+")) {
                System.out.println("La cédula debe tener solo números. Intente de nuevo");
                valor = null;
                continue;
            }

            if (valor.length() != 10) {
                System.out.println("La cédula debe tener exactamente 10 números. Intente de nuevo.");
                valor = null;
            }
        } while (valor == null);
        return valor;
    }

    // === Método principal ===
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ColaTickets cola = new ColaTickets();
        PilaAcciones acciones = new PilaAcciones();

        int opcion;
        do {
            System.out.println("\n--- Sistema CAE - Consola Principal ---");
            System.out.println("1. Registrar nuevo ticket");
            System.out.println("2. Atender caso");
            System.out.println("3. Deshacer / Rehacer acción");
            System.out.println("4. Mostrar cola de tickets");
            System.out.println("5. Consultar historial de tickets");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    // === Registrar estudiante y trámite ===
                    System.out.println("\nRegistro de nuevo estudiante");
                    String nombre = leerNombre(sc, "Nombre del estudiante");
                    String cedula = leerCedula(sc);
                    String tramite = leerNombre(sc, "Trámite (certificado, constancia, homologación, etc.)");

                    System.out.print("¿Es un caso urgente? (s/n): ");
                    boolean urgente = sc.nextLine().trim().equalsIgnoreCase("s");

                    Ticket nuevo = new Ticket(nombre, cedula, tramite);
                    cola.agregarTicket(nuevo, urgente);

                    // Registrar acción (para deshacer y rehacer)
                    acciones.registrarAccion(
                            () -> cola.agregarTicket(nuevo, urgente), // acción original
                            cola::atenderTicket // acción contraria
                    );

                    System.out.println("\n✅ Ticket generado con éxito: \n " + nuevo);
                }

                case 2 -> {
                    // Atender un ticket
                    Ticket atendido = cola.atenderTicket();

                    if (atendido != null) {
                        System.out.println("\nAtendiendo: " + atendido);

                        // Registrar acción en la pila
                        acciones.registrarAccion(
                                () -> cola.agregarTicket(atendido, false),
                                cola::atenderTicket
                        );

                        // Nota adicional
                        System.out.print("¿Desea agregar una nota durante la atención? (s/n): ");
                        String notaResp = sc.nextLine().trim().toLowerCase();
                        if (notaResp.equals("s")) {
                            System.out.print("Ingrese nota: ");
                            String notaTexto = sc.nextLine();
                            atendido.agregarNota(notaTexto);
                            System.out.println("Nota añadida al historial.");
                        }

                        // Estado final
                        System.out.print("¿El trámite se completó? (s/n): ");
                        String resp = sc.nextLine().trim().toLowerCase();

                        if (resp.equals("s")) {
                            atendido.setEstado(EstadoTicket.COMPLETADO);
                            atendido.agregarNota("Trámite completado con éxito.");
                            System.out.println("Ticket completado: " + atendido);
                        } else {
                            atendido.setEstado(EstadoTicket.PENDIENTE_DOCS);
                            System.out.print("¿Reingresar como urgente? (s/n): ");
                            boolean urgente = sc.nextLine().trim().equalsIgnoreCase("s");
                            cola.reingresarTicket(atendido, urgente);
                            System.out.println("Trámite pendiente. Ticket reingresado a la cola.\n");
                        }
                    } else {
                        System.out.println("⚠No hay casos por atender.\n");
                    }
                }

                case 3 -> {
                    // === Deshacer / Rehacer ===
                    System.out.println("\n1. Deshacer");
                    System.out.println("2. Rehacer");
                    System.out.print("Seleccione: ");
                    int sub = sc.nextInt();
                    sc.nextLine();

                    if (sub == 1) {
                        acciones.deshacer();
                    } else if (sub == 2) {
                        acciones.rehacer();
                    } else {
                        System.out.println("Opción no válida.");
                    }
                }

                case 4 -> {
                    // === Mostrar colas ===
                    cola.mostrarCola();
                }

                case 5 -> {
                    // === Mostrar historial de tickets ===
                    cola.mostrarHistorialTickets();
                }

                case 0 -> System.out.println("👋 Saliendo del sistema...");

                default -> {
                    if (opcion < 0 || opcion > 5)
                        System.out.println("⚠Opción inválida, intente nuevamente.");
                }
            }

        } while (opcion != 0);

        sc.close();
    }
}
