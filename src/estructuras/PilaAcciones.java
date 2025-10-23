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
        pilaUndo.push(() -> {
            accionContraria.run();
            pilaRedo.push(() -> accion.run()); //guarda la accion original, al deshacer
                });
        pilaRedo.clear(); //se limpia la pila de rehacer al registrar una nueva accion
    }

    public void ejecutarAccion(Runnable accion){
        accion.run();
    }

    public void deshacer() {
        if (pilaUndo.isEmpty()) {
            System.out.println("No hay acciones para deshacer.");
            return;
        }
        Runnable accion = pilaUndo.pop();
        accion.run();//ejecuta la accion contraria
        System.out.println("Acción deshecha correctamente.");
    }

    public void rehacer() {
        if (pilaRedo.isEmpty()) {
            System.out.println("No hay acciones para rehacer.");
            return;
        }
        Runnable accion = pilaRedo.pop();
        accion.run(); //ejecuta la accion original
        System.out.println("Acción rehecha correctamente");
    }
}