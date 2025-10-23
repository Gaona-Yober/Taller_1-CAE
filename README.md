# Módulo de Gestión de Tickets — CAE (Centro de Atención al Estudiante)

## Decisiones de diseño
El sistema **CAE** fue desarrollado bajo el principio de separación entre **lógica de dominio** y **lógica de interacción**:

- **Dominio:** Clases `Ticket`, `EstadoTicket`, `ListaNotas`, `ColaTickets`, `PilaAcciones`.
- **Interacción:** Clase `MainApp`, responsable del flujo y entrada de datos mediante consola.

Se implementaron tres estructuras principales:
- **Cola (`ColaTickets`)** → Controla los tickets en espera (FIFO).
- **Pila (`PilaAcciones`)** → Registra acciones para soportar *deshacer/rehacer*.
- **Lista enlazada simple (`ListaNotas`)** → Cada ticket guarda un historial de observaciones enlazadas.

Cada clase cumple con una sola responsabilidad, favoreciendo la capacidad de ampliar el código y una facilidad de lectura.

---

## Catálogo de estados del ticket

| Estado | Descripción |
|--------|-------------|
| `EN_COLA` | Ticket recién ingresado, en espera de atención. |
| `EN_ATENCION` | Caso actualmente siendo atendido. |
| `PENDIENTE_DOCS` | Requiere documentación adicional; el ticket regresa a la cola. |
| `COMPLETADO` | Trámite finalizado exitosamente. |

> Cada cambio de estado puede acompañarse de una **nota**, registrada en el historial del ticket.

---

## Casos borde manejados

- **Cola vacía:** “No hay casos en espera.”
- **Historial vacío:** “(Sin notas registradas)”.
- **Eliminar nota inexistente:** No lanza error, se omite el cambio.
- **Deshacer/Rehacer sin acciones previas:** Mensaje “Nada que deshacer/rehacer.”
- **Atender sin tickets:** Advierte al usuario que no hay casos por atender.
- **Validaciones de entrada:**
    - Cédula solo numérica y de 10 dígitos.
    - Nombres y trámites solo con letras y espacios.

---

## Guía de ejecución

1. **Compilar el proyecto:**
   ```bash
   javac modelo/*.java estructuras/*.java servicio/MainApp.java
