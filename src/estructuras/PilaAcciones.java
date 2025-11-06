package estructuras;

import java.util.Stack;

public class PilaAcciones {

    private static class Accion {
        Runnable accion;
        Runnable accionContraria;

        Accion(Runnable accion, Runnable accionContraria) {
            this.accion = accion;
            this.accionContraria = accionContraria;
        }
    }
        private final Stack<Accion> pilaUndo; //acciones ya ejecutadas y que se pueden deshacer
        private final Stack<Accion> pilaRedo; //acciones deshechas y que se pueden rehacer

        public  PilaAcciones() {
            pilaUndo = new Stack<>();
            pilaRedo = new Stack<>();
        }

        public void ejecutarAccion(Runnable accion, Runnable accionContraria) {
            accion.run();
            pilaUndo.push(new Accion(accion, accionContraria)); //Guarda la accion opuesta
            pilaRedo.clear(); //limpia el Redo cuando se registra una nueva accion

        }

        public void deshacer() {
            if (pilaUndo.isEmpty()) {
                System.out.println("No hay acciones para deshacer.");
                return;
            }
            Accion ultima = pilaUndo.pop();
            ultima.accionContraria.run();//ejecuta la accion contraria
            pilaRedo.push(ultima); //almacena la inversa
            System.out.println("Acción deshecha correctamente.");
        }

        public void rehacer() {
            if (pilaRedo.isEmpty()) {
                System.out.println("No hay acciones para rehacer.");
                return;
            }
            Accion ultima = pilaRedo.pop();
            ultima.accion.run(); //ejecuta la accion original
            pilaUndo.push(ultima);
            System.out.println("Acción rehecha correctamente");
        }

}