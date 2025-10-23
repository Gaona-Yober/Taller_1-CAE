package servicio;

import estructuras.ColaTickets;
import estructuras.PilaAcciones;
import modelo.Ticket;
import java.util.Scanner;

public class MainApp {

    //Metodo para leer los nombres (solo letras y espacios)
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

    //Metodo de validacion de cedula (solo numeros y 10 digitos)
    private static String leerCedula(Scanner sc) {
        String valor;
        do {
            System.out.print("Cédula (10 dígitos): ");
            valor = sc.nextLine().trim();
            if (!valor.matches("\\d+")){
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

    //Metodo Main
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ColaTickets cola = new ColaTickets();
        PilaAcciones acciones = new PilaAcciones();

        int opcion;
        do {
            //Menu principal
            System.out.println("\n--- Sistema CAE - Consola Principal ---");
            System.out.println("1. Datos del estudiante y Tramité");
            System.out.println("2. Atender caso");
            System.out.println("3. Deshacer / Rehacer");
            System.out.println("4. Mostrar cola");
            System.out.println("5. Consultar historial de tickets");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    //Registrar estudiante y tramite
                    System.out.println("\nRegistro de nuevo estudiante");
                    String nombre = leerNombre(sc, "Nombre del estudiante");
                    String cedula = leerCedula(sc);
                    String tramite = leerNombre(sc, "Trámite (certificado, constancia, homologación, etc.)");

                    //Generar ticket
                    Ticket nuevo = new Ticket(nombre, cedula, tramite);
                    cola.agregarTicket(nuevo);
                    acciones.registrarAccion(
                            () -> cola.agregarTicket(nuevo), //accion original
                            () -> cola.atenderTicket()  // accion contraria
                    );
                    System.out.println("\n✅ Ticket generado con éxito: \n " + nuevo);
                    break;

                case 2:
                    //Atender un ticket
                    Ticket atendido = cola.atenderTicket();

                    if(atendido != null){
                        atendido.setEstado(modelo.EstadoTicket.EN_ATENCION);
                        System.out.println("\nAtendiendo: " + atendido);

                        acciones.registrarAccion(
                                () -> cola.agregarTicket(atendido),
                                () -> cola.atenderTicket()
                        );

                        //Agregar nota
                        System.out.print("¿Desea agregar una nota durante la atención? (s/n): ");
                        String notaResp = sc.nextLine().trim().toLowerCase();
                        if (notaResp.equals("s")) {
                            System.out.print("Ingrese nota: ");
                            String notaTexto = sc.nextLine();
                            atendido.agregarNota(notaTexto);
                            System.out.println("Nota añadida al historial.");
                        }

                        System.out.print("¿El trámite se completó? (s/n): ");
                        String resp = sc.nextLine().trim().toLowerCase();

                        if (resp.equals("s")) {
                            atendido.setEstado(modelo.EstadoTicket.COMPLETADO);
                            atendido.agregarNota("Trámite completado con éxito.");
                            System.out.println("Ticket completado: " + atendido);
                        } else {
                            atendido.setEstado(modelo.EstadoTicket.PENDIENTE_DOCS);
                            //atendido.agregarNota();
                            cola.reingresarTicket(atendido);
                            System.out.println("Trámite pendiente. Ticket reingresado a la cola.\n");
                        }

                    } else {
                        System.out.println("No hay casos por atender.\n");
                    }
                    break;

                case 3:
                    //Funcion deshacer y rehacer acciones
                    System.out.println("\n1. Deshacer");
                    System.out.println("2. Rehacer");
                    System.out.print("Seleccione: ");
                    int sub = sc.nextInt();
                    sc.nextLine();

                    if (sub == 1) {
                        acciones.deshacer();
                    } else if (sub == 2) {
                        acciones.rehacer();
                    }
                    break;

                case 4:
                    //Presentar todos los tickets en cola
                    cola.mostrarCola();
                    break;

                case 5:
                    //Consultar historial
                    cola.mostrarHistorialTickets();
                    break;

                case 0:
                    //Salir del sistema
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        sc.close();
    }
}