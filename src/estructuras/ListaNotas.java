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

    public boolean eliminarNota(String texto) {
        if (cabeza == null) return false;

        if (cabeza.dato.getTexto().equals(texto)) {
            cabeza = cabeza.siguiente;
            return true;
        }

        Nodo<Nota> actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.dato.getTexto().equals(texto)) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
            return true;
        }
        return false;
    }

    public int size(){
        int count = 0;
        Nodo<Nota> actual = cabeza;
        while (actual != null){
            count++;
            actual = actual.siguiente;
        }
        return count;
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