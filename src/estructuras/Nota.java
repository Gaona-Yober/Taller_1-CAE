package estructuras;

public class Nota { //comentarios - avisos - mensajes
    private String texto;
    private Nota siguiente; // referencia al siguiente nodo

    public Nota(String texto) {
        this.texto = texto;
        this.siguiente = null;
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Nota getSiguiente() {
        return siguiente;
    }
    public void setSiguiente(Nota siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public String toString() { return texto; }


}