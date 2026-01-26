
package juegoParejas;

import java.util.*;

public class DatosJuego {

    public static JuegoParejas crearJuego() {

        // Palabras incorrectas
        List<String> palabrasIncorrectas = List.of(
                "ORDENADOR", "TECLADO", "MALETA", "OFICINA", "CARPETA", "PERSIANA", "CRISTAL", "PANTALON",
                "PUNTO", "ECUACION", "SILENCIO", "TEOREMA", "LOGICA", "DECADA", "FILOSOFIA", "MICROCHIP", "ETERNIDAD",
                "ALGORITMO", "VOLTIO", "ESTATUA", "ICONO", "ESFERA", "TEORIA", "PLEGARIA", "ORDENANZA", "HEPTAGONO",
                "PARADOJA", "INERCIA", "SINTESIS", "LIMITE", "SARTEN", "CORREO", "CORTINA", "LLAVE", "TATUAJE",
                "CUCHARA", "BOLSILLO", "ESPEJO", "CITA", "HORARIO", "TIJERA", "BOTON", "RECIBO", "ALMOHADA", "GRAPA",
                "POMO", "CENICERO", "CLAVO", "HOMBRO", "BARRA"
        );

        // Preguntas y respuestas
        Map<Integer, List<String>> preguntasRespuestas = new HashMap<>();
        preguntasRespuestas.put(0, List.of("SOL", "DIA"));
        preguntasRespuestas.put(1, List.of("MADERA", "MESA"));
        preguntasRespuestas.put(2, List.of("ANIMAL", "PERRO"));
        preguntasRespuestas.put(3, List.of("DEPORTE", "TENIS"));
        preguntasRespuestas.put(4, List.of("COLOR", "AMARILLO"));
        preguntasRespuestas.put(5, List.of("MÚSICA", "CANCION"));
        preguntasRespuestas.put(6, List.of("AGUA", "OCEANO"));
        preguntasRespuestas.put(7, List.of("FRUTA", "FRESA"));
        preguntasRespuestas.put(8, List.of("FLOR", "TULIPAN"));
        preguntasRespuestas.put(9, List.of("PISTOLA", "BALA"));

        // Pistas
        Map<Integer, String> pistas = new HashMap<>();
        pistas.put(0, "El periodo de tiempo en que esta estrella nos ilumina");
        pistas.put(1, "Mueble común que suele estar fabricado con este material");
        pistas.put(2, "Ejemplo de un mamífero doméstico muy leal");
        pistas.put(3, "Actividad física que se juega golpeando una pelota con raqueta");
        pistas.put(4, "El tono que representa la luz de esta categoría");
        pistas.put(5, "Una composición sonora que se puede cantar");
        pistas.put(6, "Gran masa de este líquido que cubre el planeta");
        pistas.put(7, "Un ejemplo de alimento dulce, rojo y con semillas");
        pistas.put(8, "Tipo de planta ornamental conocida por su forma de copa");
        pistas.put(9, "El proyectil que sale disparado desde este objeto");

        return new JuegoParejas(preguntasRespuestas, pistas, palabrasIncorrectas);
    }
}
