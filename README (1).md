
# 📌 Sistema CAE - Gestión de Tickets (Consola)

Este proyecto es una aplicación en Java que simula un sistema básico de gestión de tickets para atención de casos.  
Permite registrar tickets, atenderlos en orden de llegada (FIFO), y realizar acciones de deshacer/rehacer mediante una pila.

---

## 🚀 Características principales

✅ Registrar tickets en una **cola** (implementación FIFO).  
✅ Atender tickets (se remueven de la cola y pasan a historial).  
✅ Función **deshacer / rehacer** basada en una pila de acciones.  
✅ Mostrar la cola actual de tickets pendientes.  
✅ Mostrar historial de tickets (pendientes y atendidos).  
✅ Exportar o visualizar tickets atendidos.

---

## 🧠 Arquitectura del sistema

El programa se estructura usando tres componentes principales:

| Componente | Descripción |
|------------|-------------|
| `ColaTickets` | Estructura que almacena los tickets en orden de llegada (cola FIFO). |
| `PilaAcciones` | Estructura basada en pila para registrar acciones y permitir deshacer/rehacer. |
| `ControladorTiket` | Controlador que contiene la lógica de negocio (registrar, atender, deshacer/rehacer). |

El archivo `MainApp` gestiona el menú principal e interactúa con el controlador.

---

## 📂 Estructura del proyecto

```
src/
├── estructuras/
│   ├── ColaTickets.java
│   └── PilaAcciones.java
├── modelo/
│   ├── Ticket.java
│   └── EstadoTicket.java
├── servicio/
│   ├── ControladorTiket.java
│   └── MainApp.java
```

---

## 🧾 Uso del sistema

Al ejecutar la aplicación (`MainApp`), aparecerá un menú como este:

```
--- Sistema CAE - Consola Principal ---
1. Registrar nuevo ticket
2. Atender caso
3. Deshacer / Rehacer acción
4. Mostrar cola de tickets
5. Consultar historial de tickets
6. Mostrar tickets atendidos y exportar
0. Salir
```

### 📌 Opciones del Menú

| Opción | Acción |
|--------|--------|
| **1. Registrar nuevo ticket** | Solicita datos del usuario y lo agrega a la cola de atención. |
| **2. Atender caso** | Extrae el ticket más antiguo de la cola y lo pasa al historial como atendido. |
| **3. Deshacer / Rehacer acción** | Revierte la última acción o la aplica nuevamente según sea necesario. |
| **4. Mostrar cola de tickets** | Lista los tickets pendientes por atender. |
| **5. Consultar historial de tickets** | Muestra todos los tickets registrados (pendientes + atendidos). |
| **6. Mostrar tickets atendidos y exportar** | Lista los tickets ya atendidos (opcional: exportación a archivo). |
| **0. Salir** | Cierra el programa. |

---

## ✅ Ejecución

Compilar:

```sh
javac servicio/MainApp.java
```

Ejecutar:

```sh
java servicio.MainApp
```

---

## ✨ Contribuciones

Las contribuciones o mejoras son bienvenidas. Siéntete libre de crear issues o pull requests.

---

## 📄 Licencia

Este proyecto es de uso académico y educativo.

---
