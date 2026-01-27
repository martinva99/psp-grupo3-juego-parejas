package juegoParejas;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

/**
 * POJO que encapsula la lógica del juego WordMatch.
 * Gestiona las preguntas, respuestas, opciones incorrectas y pistas.
 * <p>
 * Esta clase no depende de red, hilos ni E/S, lo que permite
 * su testeo mediante pruebas unitarias.
 */
public class JuegoParejas {

    private final Map<Integer, List<String>> preguntasRespuestas;
    private final Map<Integer, String> pistas;
    private final List<String> palabrasIncorrectas;

    private int preguntaActual = -1;
    private List<String> opciones;

    /**
     * Constructor del juego.
     *
     * @param preguntasRespuestas Mapa con las parejas pregunta-respuesta
     * @param pistas              Mapa de pistas asociadas a cada pregunta
     * @param palabrasIncorrectas Lista de palabras incorrectas para generar opciones
     */
    public JuegoParejas(
            Map<Integer, List<String>> preguntasRespuestas,
            Map<Integer, String> pistas,
            List<String> palabrasIncorrectas) {

        this.preguntasRespuestas = preguntasRespuestas;
        this.pistas = pistas;
        this.palabrasIncorrectas = palabrasIncorrectas;
    }

    /**
     * Devuelve el índice de la pregunta actual.
     *
     * @return índice de la pregunta actual
     */
    public int getPreguntaActual() {
        return preguntaActual;
    }

    /**
     * Avanza a la siguiente pregunta y genera las opciones.
     *
     * @return la pregunta con sus opciones o "FIN" si no quedan preguntas
     */
    public String nuevaPregunta() {
        preguntaActual++;
        if (preguntaActual >= preguntasRespuestas.size()) {
            return "FIN";
        }

        generarOpciones();
        return getPreguntaConOpciones();
    }

    /**
     * Genera una lista de tres opciones (una correcta y dos incorrectas).
     */
    private void generarOpciones() {
        Random r = new Random();
        opciones = new ArrayList<>();
        opciones.add(getRespuestaCorrecta());

        while (opciones.size() < 3) {
            String incorrecta = palabrasIncorrectas.get(r.nextInt(palabrasIncorrectas.size()));
            if (!opciones.contains(incorrecta)) {
                opciones.add(incorrecta);
            }
        }

        Collections.shuffle(opciones);
    }

    /**
     * Comprueba si la respuesta del usuario es correcta.
     *
     * @param respuesta respuesta enviada por el cliente
     * @return "OK", "ERROR" o mensaje de error si aún no hay pregunta
     */
    public String responder(String respuesta) {
        if (getPreguntaActual() == -1) {
            return "NO SE PUEDE DAR UNA RESPUESTA ANTES DE LA PRIMERA PREGUNTA";
        }

        if (quitarTildes(respuesta).trim().equalsIgnoreCase("RESPUESTA " + getRespuestaCorrecta())) {
            return "OK";
        }
        return "ERROR";
    }

    /**
     * Devuelve la pista de la pregunta actual.
     *
     * @return pista correspondiente o mensaje de error si aún no hay pregunta
     */
    public String pedirPista() {
        if (getPreguntaActual() == -1) {
            return "NO SE PUEDE PEDIR PISTA ANTES DE LA PRIMERA PREGUNTA";
        } else {
            return "PISTA: " + pistas.get(preguntaActual);
        }
    }

    /**
     * Devuelve la pregunta actual con sus opciones.
     *
     * @return pregunta formateada para el protocolo
     */
    private String getPreguntaConOpciones() {
        return "PREGUNTA: " + getPregunta() +
                " || OPCIONES: " + String.join(" ", opciones);
    }

    /**
     * Devuelve el texto de la pregunta actual.
     *
     * @return pregunta
     */
    private String getPregunta() {
        return preguntasRespuestas.get(preguntaActual).get(0);
    }

    /**
     * Devuelve la respuesta correcta de la pregunta actual.
     *
     * @return respuesta correcta
     */
    private String getRespuestaCorrecta() {
        return preguntasRespuestas.get(preguntaActual).get(1);
    }

    /**
     * Elimina tildes de una cadena de texto.
     *
     * @param texto texto original
     * @return texto sin tildes
     */
    public static String quitarTildes(String texto) {
        String normalizedString = Normalizer.normalize(texto, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedString).replaceAll("");
    }
}
