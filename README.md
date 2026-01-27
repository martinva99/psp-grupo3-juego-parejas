# Juego de Parejas (PSP) - Grupo 3 (Carlos, Gael y Martín)

El sistema implementa un **servidor multihilo** que gestiona partidas independientes de un juego de emparejar palabras, 
y clientes que se comunican mediante un **protocolo TCP/IP**.

---

## Tecnologías utilizadas

- Java
- JUnit 5
- Git / GitHub

---

## Ejecución del proyecto

### 1. Arrancar el servidor

Ejecutar la clase ServidorParejas

El servidor quedará escuchando en el puerto **54321**.

---

### 2. Arrancar uno o varios clientes

Ejecutar la clase ClienteParejas

Se pueden ejecutar varios clientes simultáneamente.

---

## Protocolo de comunicación

Al conectarse, el servidor envía: WORDMATCH LISTO

### Comandos disponibles

| Comando | Descripción |
|-------|------------|
| `NUEVA` | Solicita una nueva pregunta |
| `RESPUESTA X` | Responde a la pregunta actual |
| `PISTA` | Solicita una pista |
| `SALIR` | Finaliza la conexión |

---

### Ejemplo

NUEVA

PREGUNTA: SOL || OPCIONES: DIA NOCHE LLUVIA

PISTA

PISTA: El periodo de tiempo en que esta estrella nos ilumina

RESPUESTA DIA

OK

SALIR

GRACIAS POR JUGAR

---

## Tests

### Tests unitarios
- Verifican la lógica del juego (`JuegoParejas`)

### Tests de integración
- Arrancan el servidor real
- Simulan clientes mediante sockets
- Verifican la comunicación servidor–cliente y el protocolo

---
