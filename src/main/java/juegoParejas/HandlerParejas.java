package juegoParejas;

import java.io.*;
import java.net.*;

/**
 * Handler que gestiona la comunicación con un cliente.
 * Cada instancia se ejecuta en un hilo independiente.
 */
class HandlerParejas implements Runnable {

    private final Socket socket;

    /**
     * Constructor del handler.
     *
     * @param socket socket del cliente
     */
    public HandlerParejas(Socket socket) {
        this.socket = socket;
    }

    /**
     * Lógica principal del cliente:
     * - Lee comandos
     * - Ejecuta la lógica del juego
     * - Devuelve respuestas según el protocolo
     */
    @Override
    public void run() {

        JuegoParejas juego = DatosJuego.crearJuego();

        try (socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("WORDMATCH LISTO");

            String linea;
            while ((linea = in.readLine()) != null) {

                if (linea.equalsIgnoreCase("SALIR")) {
                    out.println("GRACIAS POR JUGAR");
                    break;
                } else if (linea.equalsIgnoreCase("NUEVA")) {
                    String nueva = juego.nuevaPregunta();

                    if (nueva.equalsIgnoreCase("FIN")) {
                        out.println("NO HAY MÁS PREGUNTAS. GRACIAS POR JUGAR");
                        break;
                    }
                    out.println(nueva);

                } else if (linea.equalsIgnoreCase("PISTA")) {
                    out.println(juego.pedirPista());
                } else if (linea.toUpperCase().trim().startsWith("RESPUESTA ")) {
                    out.println(juego.responder(linea));
                } else {
                    out.println("COMANDO NO RECONOCIDO");
                }

            }

        } catch (IOException e) {
            System.out.println("Cliente desconectado");
        }
    }
}
