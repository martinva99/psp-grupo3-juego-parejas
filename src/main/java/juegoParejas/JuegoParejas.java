package juegoParejas;

import java.util.*;

public class JuegoParejas {

    private final Map<Integer, List<String>> preguntasRespuestas;
    private final Map<Integer, String> pistas;
    private final List<String> palabrasIncorrectas;

    private int preguntaActual = -1;
    private List<String> opciones;

    public JuegoParejas(
            Map<Integer, List<String>> preguntasRespuestas,
            Map<Integer, String> pistas,
            List<String> palabrasIncorrectas) {

        this.preguntasRespuestas = preguntasRespuestas;
        this.pistas = pistas;
        this.palabrasIncorrectas = palabrasIncorrectas;
    }

    public String nuevaPregunta() {
        preguntaActual++;
        if (preguntaActual >= preguntasRespuestas.size()) {
            return "FIN";
        }

        generarOpciones();
        return getPreguntaConOpciones();
    }

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

    public String responder(String respuesta) {
        if (respuesta.equalsIgnoreCase(getRespuestaCorrecta())) {
            return "OK";
        }
        return "ERROR";
    }

    public String pedirPista() {
        return "PISTA: " + pistas.get(preguntaActual);
    }

    private String getPreguntaConOpciones() {
        return "PREGUNTA: " + getPregunta() +
                " || OPCIONES: " + String.join(" ", opciones);
    }

    private String getPregunta() {
        return preguntasRespuestas.get(preguntaActual).get(0);
    }

    private String getRespuestaCorrecta() {
        return preguntasRespuestas.get(preguntaActual).get(1);
    }
}
