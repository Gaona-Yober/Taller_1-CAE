package estructuras;

public class ListaNotas {
    private Nodo<Nota> cabeza;


    //nueva nota
    public void agregarNota(String texto) {
        Nota nuevaNota = new Nota(texto);
        Nodo<Nota> nuevo = new Nodo<>(nuevaNota);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
    }

    public void eliminarNota(String texto) {
        if (cabeza == null) return;

        if (cabeza.dato.getTexto().equals(texto)) {
            cabeza = cabeza.siguiente;
            return;
        }

        Nodo<Nota> actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.dato.getTexto().equals(texto)) {
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

        Nodo<Nota> actual = cabeza;
        while (actual != null) {
            System.out.println("   • " + actual.dato.getTexto());
            actual = actual.siguiente;
        }
    }
}