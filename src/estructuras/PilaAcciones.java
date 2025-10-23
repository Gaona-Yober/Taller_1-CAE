package estructuras;

import java.util.Stack;

public class PilaAcciones<T> {
    private Stack<T> pilaUndo; //acciones ya ejecutadas y que se pueden deshacer
    private Stack<T> pilaRedo; //acciones deshechas y que se pueden rehacer

    public PilaAcciones() {
        pilaUndo = new Stack<>();
        pilaRedo = new Stack<>();
    }

    public void registrarAccion(T accion) {
        pilaUndo.push(accion); //nueva accion (del usuario) ejecutada
        pilaRedo.clear(); //se limpia la pila de rehacer al registrar una nueva accion
    }

    public T deshacer() {
        if (pilaUndo.isEmpty()) return null;
        T accion = pilaUndo.pop(); //ultima accion ejecutada de Undo
        pilaRedo.push(accion);    //se agrega a la pila de Redo para poder rehacerla
        return accion;
    }

    public T rehacer() {
        if (pilaRedo.isEmpty()) return null;
        T accion = pilaRedo.pop(); //ultima accion deshecha de Redo
        pilaUndo.push(accion);  //se agrega a la pila de Undo para poder deshacerla nuevamente
        return accion;
    }
}