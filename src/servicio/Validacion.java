package servicio;

import java.util.Scanner;

public class Validacion {

    public static String leerNombre(Scanner sc, String campo) {
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

    public static String leerCedula(Scanner sc) {
        String valor;
        do {
            System.out.print("Ingrese Cédula (10 dígitos): ");
            valor = sc.nextLine().trim();

            if (!valor.matches("\\d{10}")) {
                System.out.println("La cédula debe tener exactamente 10 números.");
                valor = null;
            }

        } while (valor == null);
        return valor;
    }

    public static String leerPasaporte(Scanner sc) {
        String valor;
        do {
            System.out.print("Ingrese Pasaporte (letras y números, 6-15 caracteres): ");
            valor = sc.nextLine().trim();

            // Pasaporte: permite letras y números, entre 6 y 15 caracteres
            if (!valor.matches("^[A-Za-z0-9]{6,15}$")) {
                System.out.println("Pasaporte inválido. Solo letras y números (6 a 15 caracteres).");
                valor = null;
            }

        } while (valor == null);
        return valor;
    }

    // *** NUEVO MÉTODO ***
    public static String leerIdentificacion(Scanner sc) {
        System.out.println("\nTipo de identificación:");
        System.out.println("1. Cédula");
        System.out.println("2. Pasaporte");

        int opcion = leerOpcionMenu(sc, 1, 2);

        if (opcion == 1) {
            return leerCedula(sc);
        } else {
            return leerPasaporte(sc);
        }
    }

    public static int leerOpcionMenu(Scanner sc, int min, int max) {
        int opcion = -1;
        boolean valida = false;

        do {
            System.out.print("Seleccione una opción: ");
            String entrada = sc.nextLine().trim();
            try {
                opcion = Integer.parseInt(entrada);
                if (opcion < min || opcion > max)
                    System.out.println("Error, no existe dentro de las opciones");
                else
                    valida = true;

            } catch (NumberFormatException e) {
                System.out.println("Error, ingrese un valor numérico");
            }
        } while (!valida);

        return opcion;
    }
}
