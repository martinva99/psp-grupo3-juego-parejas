package juegoParejas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestIntegracion {

    private JuegoParejas juego;

    @BeforeEach
    void setUp() {
        juego = DatosJuego.crearJuego();
    }

    /*
    Cosas que probar:
        - Cliente conecta con servidor
        - Lógica del Handler
        - El juego se comporta bien con el handler
     */
}
