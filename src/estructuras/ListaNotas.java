package estructuras;

public class ListaNotas {
    private Nodo cabeza;


    //nueva nota
    public void agregarNota(String texto) {
        Nodo nueva = new Nodo(texto);
        nueva.siguiente = cabeza;
        cabeza = nueva;
    }

    public void eliminarNota(String texto) {
        if (cabeza == null) return;

        if (cabeza.texto.equals(texto)) {
            cabeza = cabeza.siguiente;
            return;
        }

        Nodo actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.texto.equals(texto)) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
        }
    }

    public void mostrarNotas() {
        if (cabeza == null) {
            System.out.println("   (Sin notas registradas)");
            return;
        }

        Nodo actual = cabeza;
        while (actual != null) {
            System.out.println("   • " + actual.texto);
            actual = actual.siguiente;
        }
    }

    private static class Nodo {
        String texto;
        Nodo siguiente;

        Nodo(String texto) {
            this.texto = texto;
        }
    }
}