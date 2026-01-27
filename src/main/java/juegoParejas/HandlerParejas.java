package juegoParejas;

import java.io.*;
import java.net.*;

class HandlerParejas implements Runnable {
    private final Socket socket;

    public HandlerParejas(Socket socket) {
        this.socket = socket;
    }

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
                    if (juego.nuevaPregunta().equalsIgnoreCase("FIN")) {
                        out.println("NO HAY MÁS PREGUNTAS. GRACIAS POR JUGAR");
                        break;
                    }
                    out.println(juego.nuevaPregunta());
                } else if (linea.equalsIgnoreCase("PISTA")) {
                    out.println(juego.pedirPista());
                } else if (linea.toUpperCase().trim().startsWith("RESPUESTA ")){
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
