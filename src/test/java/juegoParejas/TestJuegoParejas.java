package juegoParejas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios de la clase {@link JuegoParejas}.
 * Verifican únicamente la lógica del juego sin red ni hilos.
 */
public class TestJuegoParejas {
    private JuegoParejas juego;

    @BeforeEach
    void setUp() {
        juego = DatosJuego.crearJuego();
    }

    @Test
    void testNuevaPreguntaTieneTodasLasOpciones() {
        String resultado = juego.nuevaPregunta();

        assertTrue(resultado.startsWith("PREGUNTA:"), "Las preguntas deben empezar por PREGUNTA:");
        assertTrue(resultado.contains("OPCIONES:"), "Las preguntas deben contener OPCIONES:");
        assertEquals(7, resultado.split(" ").length, "Debe haber 3 opciones");
    }

    @Test
    void testNuevaPreguntaPrimeraPregunta() {
        String resultado = juego.nuevaPregunta();

        assertTrue(resultado.startsWith("PREGUNTA: SOL || OPCIONES: "),
                "La primera pregunta debe empezar por 'PREGUNTA: SOL || OPCIONES: '");
        assertTrue(resultado.contains("DIA"),
                "La primera pregunta debe contener 'DIA' entre las opciones");
    }

    @Test
    void testNuevaPreguntaSegundaPregunta() {
        juego.nuevaPregunta(); // Simula la primera pregunta
        String resultado = juego.nuevaPregunta();

        assertTrue(resultado.startsWith("PREGUNTA: MADERA || OPCIONES: "),
                "La segunda pregunta debe empezar por 'PREGUNTA: MADERA || OPCIONES: '");
        assertTrue(resultado.contains("MESA"),
                "La segunda pregunta debe contener 'MESA' entre las opciones");
    }

    @Test
    void testNuevaPreguntaUltimaPregunta() {
        for (int i = 0; i < 9; i++) { // Simula el resto de preguntas
            juego.nuevaPregunta();
        }
        String resultado = juego.nuevaPregunta();


        assertTrue(resultado.startsWith("PREGUNTA: PISTOLA || OPCIONES: "),
                "La última pregunta debe empezar por 'PREGUNTA: PISTOLA || OPCIONES: '");
        assertTrue(resultado.contains("BALA"),
                "La última pregunta debe contener 'BALA' entre las opciones");
    }

    @Test
    void testNuevaPreguntaFinJuego() {
        for (int i = 0; i < 10; i++) { // Simula todas las preguntas
            juego.nuevaPregunta();
        }
        String resultado = juego.nuevaPregunta();

        assertEquals("FIN", resultado);
    }

    @Test
    void testPedirPistaPrimeraPregunta() {
        juego.nuevaPregunta();
        String resultado = juego.pedirPista();

        assertTrue(resultado.startsWith("PISTA: El periodo de tiempo"),
                "La pista debe ser la de la primera pregunta");
    }

    @Test
    void testPedirPistaSegundaPregunta() {
        juego.nuevaPregunta();
        juego.nuevaPregunta();
        String resultado = juego.pedirPista();

        assertTrue(resultado.startsWith("PISTA: Mueble común"),
                "La pista debe ser la de la segunda pregunta");
    }

    @Test
    void testPedirPistaPronto() {
        String resultado = juego.pedirPista();

        assertTrue(resultado.startsWith("NO SE PUEDE PEDIR"),
                "Problema con la respuesta al pedir pista cuando todavía no se debería");
    }

    @Test
    void testResponderCorrectamente() {
        juego.nuevaPregunta();
        assertEquals("OK", juego.responder("RESPUESTA DIA"),
                "Problema con la respuesta correcta");
        assertEquals("OK", juego.responder("RESPUESTA dia"),
                "Problema con las minúsculas");
        assertEquals("OK", juego.responder("RESPUESTA DÍA"),
                "Problema con las tildes");
    }

    @Test
    void testResponderIncorrectamente() {
        juego.nuevaPregunta();
        assertEquals("ERROR", juego.responder("RESPUESTA SARTEN"),
                "Debe dar error con respuesta incorrecta");
        assertEquals("ERROR", juego.responder("RESPUESTA MESA"),
                "Problema con respuesta correcta de OTRA pregunta (debería ser error)");
    }
}
