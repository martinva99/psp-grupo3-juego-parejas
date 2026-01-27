package juegoParejas;

import java.io.*;
import java.net.*;

/**
 * Servidor multihilo del juego WordMatch.
 * Acepta conexiones de clientes y delega cada una
 * a un {@link HandlerParejas}.
 */
public class ServidorParejas {

    private static final int PUERTO = 54321;
    private static volatile boolean activo = true;

    /**
     * Punto de entrada del servidor.
     *
     * @throws IOException si ocurre un error al abrir el socket
     */
    public static void main(String[] args) throws IOException {
        //Hook de apagado para cerrar con ctrl+c
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n ---");
            activo = false;
        }));

        try (ServerSocket srv = new ServerSocket(PUERTO)) {
            srv.setSoTimeout(2000);
            System.out.println("--- Inicializando servidor juego parejas ---");

            while (activo) {
                try {
                    Socket cliente = srv.accept();
                    System.out.println("Nuevo cliente conectado: " + cliente.getInetAddress());

                    new Thread(new HandlerParejas(cliente)).start();
                } catch (IOException e) {
                    System.out.print(".");
                }
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
        System.out.println("Servidor detenido y puerto liberado.");

    }


}
