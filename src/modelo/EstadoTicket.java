package modelo;
public enum EstadoTicket {
    EN_COLA,
    EN_ATENCION,
    PENDIENTE_DOCS,
    COMPLETADO;

    /**
     * Determina si la transición de estadoActual hacia nuevoEstado es válida.
     */
    public static boolean esTransicionValida(EstadoTicket estadoActual, EstadoTicket nuevoEstado) {
        if (estadoActual == null || nuevoEstado == null) return false;

        switch (estadoActual) {
            case EN_COLA:
                return nuevoEstado == EN_ATENCION;
            case EN_ATENCION:
                return nuevoEstado == PENDIENTE_DOCS || nuevoEstado == COMPLETADO;
            case PENDIENTE_DOCS:
                return nuevoEstado == COMPLETADO;
            case COMPLETADO:
                return false; // No se permite salir de COMPLETADO
            default:
                return false;
        }
    }
}

