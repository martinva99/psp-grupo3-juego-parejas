package juegoParejas;

import org.junit.jupiter.api.*;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración del sistema WordMatch.
 * <p>
 * Arranca el servidor real y simula clientes mediante sockets
 * para comprobar el protocolo y la comunicación completa.
 */
class TestIntegracion {

    private static Thread servidorThread;

    /**
     * Arranca el servidor antes de ejecutar los tests.
     */
    @BeforeAll
    static void iniciarServidor() {
        servidorThread = new Thread(() -> {
            try {
                ServidorParejas.main(null);
            } catch (IOException e) {
            }
        });

        servidorThread.setDaemon(true);
        servidorThread.start();

        // Esperar a que el servidor arranque
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }

    @Test
    void testConexionInicial() throws IOException {
        try (Socket socket = new Socket("localhost", 54321);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String respuesta = in.readLine();
            assertEquals("WORDMATCH LISTO", respuesta,
                    "El programa debe mostrar 'WORDMATCH LISTO' al empezar");
        }
    }

    @Test
    void testNuevaPregunta() throws IOException {
        try (Socket socket = new Socket("localhost", 54321);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            in.readLine(); // WORDMATCH LISTO

            out.println("NUEVA");
            String respuesta = in.readLine();

            assertTrue(respuesta.startsWith("PREGUNTA:"), "La pregunta debe empezar por 'PREGUNTA:'");
            assertTrue(respuesta.contains("SOL"), "Debe contener la primera pregunta");
        }
    }

    @Test
    void testSegundaPregunta() throws IOException {
        try (Socket socket = new Socket("localhost", 54321);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            in.readLine();

            out.println("NUEVA");
            in.readLine();

            out.println("NUEVA");
            String segunda = in.readLine();

            assertTrue(segunda.startsWith("PREGUNTA:"), "La pregunta debe empezar por 'PREGUNTA:'");
            assertTrue(segunda.contains("MADERA"), "Debe contener la segunda pregunta");
        }
    }

    @Test
    void testUltimaPregunta() throws IOException {
        try (Socket socket = new Socket("localhost", 54321);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            in.readLine();

            for (int i = 0; i < 9; i++) { // Simular el resto de preguntas
                out.println("NUEVA");
                in.readLine();
            }

            out.println("NUEVA");
            String ultima = in.readLine();

            assertTrue(ultima.startsWith("PREGUNTA:"), "La pregunta debe empezar por 'PREGUNTA:'");
            assertTrue(ultima.contains("PISTOLA"), "Debe contener la última pregunta");
        }
    }

    @Test
    void testFinDelJuego() throws IOException {
        try (Socket socket = new Socket("localhost", 54321);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            in.readLine();

            // Simular todas las preguntas
            for (int i = 0; i < 10; i++) {
                out.println("NUEVA");
                in.readLine();
            }

            out.println("NUEVA");
            String fin = in.readLine();

            assertTrue(fin.startsWith("NO HAY MÁS PREGUNTAS"),
                    "Debe mostrar el mensaje de que ya no hay más preguntas.");
        }
    }

    @Test
    void testResponderCorrectamente() throws IOException {
        try (Socket socket = new Socket("localhost", 54321);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            in.readLine();

            out.println("NUEVA");
            in.readLine();

            out.println("RESPUESTA DIA");
            String respuesta = in.readLine();

            assertEquals("OK", respuesta, "Problema con la respuesta correcta");
        }
    }

    @Test
    void testResponderIncorrectamente() throws IOException {
        try (Socket socket = new Socket("localhost", 54321);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            in.readLine();

            out.println("NUEVA");
            in.readLine();

            out.println("RESPUESTA SARTEN");
            String respuesta = in.readLine();

            assertEquals("ERROR", respuesta, "Problema con respuesta incorrecta");
        }
    }

    @Test
    void testComandoIncorrecto() throws IOException {
        try (Socket socket = new Socket("localhost", 54321);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            in.readLine();

            out.println("HOLA");
            String respuesta = in.readLine();

            assertEquals("COMANDO NO RECONOCIDO", respuesta, "Problema con comando incorrecto");
        }
    }

    @Test
    void testSalir() throws IOException {
        try (Socket socket = new Socket("localhost", 54321);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            in.readLine();

            out.println("SALIR");
            String respuesta = in.readLine();

            assertEquals("GRACIAS POR JUGAR", respuesta, "Comando salir debe mostrar despedida");
        }
    }
}
