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

    ![Diagrama del sistema](img/img.1.png)


2. **Generar un ticket:**

    ![Diagrama del sistema](img/img.2.png)


3. **Atender un caso:**

    ![Diagrama del sistema](img/img.3.png)


4. **Deshacer acción:**

    ![Diagrama del sistema](img/img.4.png)


5. **Rehacer acción:**

    ![Diagrama del sistema](img/img.5.png)


6. **Mostrar tickets en cola:**

    ![Diagrama del sistema](img/img.6.png)


7. **Consultar el historial de los tickets:**

    ![Diagrama del sistema](img/img.7.png)


8. **Salir del sistema:**

    ![Diagrama del sistema](img/img.8.png)

   
