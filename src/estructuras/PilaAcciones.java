package estructuras;

import java.util.Stack;

public class PilaAcciones {
    private Stack<Runnable> pilaUndo; //acciones ya ejecutadas y que se pueden deshacer
    private Stack<Runnable> pilaRedo; //acciones deshechas y que se pueden rehacer

    public PilaAcciones() {
        pilaUndo = new Stack<>();
        pilaRedo = new Stack<>();
    }

    public void registrarAccion(Runnable accion, Runnable accionContraria) {
        accion.run();
        pilaUndo.push(accionContraria); //Guarda la accion opuesta
        pilaRedo.clear(); //limpia el Redo cuando se registra una nueva accion

    }

   public void deshacer() {
        if (pilaUndo.isEmpty()) {
            System.out.println("No hay acciones para deshacer.");
            return;
        }
        Runnable accionContraria = pilaUndo.pop();
        accionContraria.run();//ejecuta la accion contraria
       pilaRedo.push(accionContraria); //almacena la inversa
        System.out.println("Acción deshecha correctamente.");
    }

    public void rehacer() {
        if (pilaRedo.isEmpty()) {
            System.out.println("No hay acciones para rehacer.");
            return;
        }
        Runnable accion = pilaRedo.pop();
        accion.run(); //ejecuta la accion original
        pilaUndo.push(accion);
        System.out.println("Acción rehecha correctamente");
    }
}