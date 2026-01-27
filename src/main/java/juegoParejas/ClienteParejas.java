package juegoParejas;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Cliente del juego WordMatch.
 * Permite al usuario interactuar con el servidor
 * mediante comandos por consola.
 */
public class ClienteParejas {

    /**
     * Punto de entrada del cliente.
     */
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 54321;
        boolean conectado = true;

        try (Socket socket = new Socket(host, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner sc = new Scanner(System.in)) {

            System.out.println("// ------------ INICIANDO WORDMATCH ------------ //\n");

            // COMPROBACIÓN DEL SERVIDOR - ESPERA RESPUESTA INICIAL
            String mensajeServidor = in.readLine();
            System.out.println("[SERVER] OPCIONES DISPONIBLES\n " + opcionesMenu());
            System.out.println("[SERVER] " + mensajeServidor);

            // COMUNICACIÓN CONTROLADA
            while (conectado) {
                System.out.print("> ");
                String comando = sc.nextLine().trim();

                // EVITAR LÍNEAS EN BLANCO
                if (comando.isEmpty()) {
                    continue;
                }

                // SACA EL MENSAJE POR PANTALLA
                out.println(comando);

                // SERVER RECIBE LA RESPUESTA
                String respuesta = in.readLine();

                // VALIDAR
                if (respuesta == null) {
                    System.out.println("SERVIDOR CERRÓ LA CONEXIÓN");
                    conectado = false;
                } else {
                    System.out.println("[SERVER] - " + respuesta);
                }

                // USUARIO QUIERE SALIR
                if (comando.equalsIgnoreCase("SALIR")) {
                    conectado = false;
                }

                // SALIR SI SE ACABAN LAS PREGUNTAS
                if (respuesta.equals("NO HAY MÁS PREGUNTAS. GRACIAS POR JUGAR")) {
                    conectado = false;
                }

            }

        } catch (ConnectException e) {
            System.out.println("Error: No se pudo conectar al servidor.");
        } catch (IOException e) {
            System.out.println("Error de comunicación.");
        }

        System.out.println("// -------------- Cliente desconectado -------------- // ");
    }

    /**
     * Devuelve el menú de opciones disponibles para el usuario.
     *
     * @return texto del menú
     */
    private static String opcionesMenu() {
        return
                "  NUEVA - Para empezar a jugar al WordMatch y mostrar una nueva pregunta\n" +
                        "   RESPUESTA X - Para indicar la respuesta de la pregunta (formato ejemplo: RESPUESTA MADERA)\n" +
                        "   PISTA - Muestra una pista si no estas seguro/a de la respuesta\n" +
                        "   SALIR - Si quieres dejar de jugar\n\n";
    }

}


