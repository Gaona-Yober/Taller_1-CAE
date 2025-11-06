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
            System.out.print("Cédula (10 dígitos): ");
            valor = sc.nextLine().trim();

            if (!valor.matches("\\d{10}")) {
                System.out.println("La cédula debe tener 10 números.");
                valor = null;
            }

        } while (valor == null);
        return valor;
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
