package juegoParejas;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

/*
 * CLASE DE PRUEBA (Primera versión)
 * NO INFLUYE EN NADA EN EL FUNCIONAMIENTO DEL PROGRAMA
 * ESTÁ SOLAMENTE A MODO DE ANÉCDOTA (para que se vea por dónde empezamos)
 */

public class PruebaLogica {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> palabrasIncorrectas = new ArrayList<>(); //Lista de palabras incorrectas
        List<String> preguntas = new ArrayList<>(); // Lista de palabras pregunta-respuesta
        Map<Integer, List<String>> preguntasRespuestas = new HashMap(); //Mapa con clave numérica y valor dos Strings
        Map<Integer, String> pistas = new HashMap(); // mapa para pistas

        pistas.put(0, "El periodo de tiempo en que esta estrella nos ilumina");
        pistas.put(1, "Mueble común que suele estar fabricado con este material");
        pistas.put(2, "Ejemplo de un mamífero doméstico muy leal");
        pistas.put(3, "Actividad física que se juega golpeando una pelota con raqueta");
        pistas.put(4, "El color de PAC-MAN");
        pistas.put(5, "Una composición sonora que se puede cantar");
        pistas.put(6, "Gran masa de este líquido que cubre el planeta");
        pistas.put(7, "Un ejemplo de alimento dulce, rojo y con semillas");
        pistas.put(8, "Tipo de planta ornamental conocida por su forma de copa");
        pistas.put(9, "El proyectil que sale disparado desde este objeto");

        // Ejemplo del HashMap:
        // (0, ("SOL", "DÍA"))
        // (1, ("MADERA", "MESA"))
        // La primera posición del valor del Hashmap siempre es la pregunta, y la segunda la respuesta

        for (listadoPalabras palabra : listadoPalabras.values()) { // meter lista de palabras de "relleno"/"incorrectas" al ArrayList
            palabrasIncorrectas.add(palabra.toString());
        }
        for (listadoPreguntas palabra : listadoPreguntas.values()) { // meter lista de palabras pregunta-respuesta a una lista
            preguntas.add(palabra.toString());
        }
        for (int i = 0; i < 10; i++) { // meter la lista de palabras pregunta-respuesta al HashMap (clave del HashMap es un número, valor del HashMap es una lista de dos Strings: pregunta-respuesta
            List<String> preguntasTemporal = new ArrayList<>(); // Lista temporal para meter las pregunta-respuesta al hashmap
            preguntasTemporal.add(preguntas.get(i * 2));
            preguntasTemporal.add(preguntas.get(i * 2 + 1));
            preguntasRespuestas.put(i, preguntasTemporal);
        }

        //Simulación de cómo funcionaría el programa, sin tener en cuenta servidor ni cliente
        System.out.println("WORDMATCH LISTO");

        Random rand = new Random(); // Para aleatorizar los números
        int opcionesRandom; // número aleatorio para meter en la lista de opciones
        int palabrasRandom; // número aleatorio para meter palabras incorrectas
        String respuesta;

        for (int i = 0; i < preguntasRespuestas.size(); i++) {
            StringBuilder sb = new StringBuilder();
            List<String> opciones = new ArrayList<>(); // Lista para meter opciones cada vez que se juega
            System.out.println("Escriba 'NUEVA' para  recibir una nueva pregunta, " +
                    "o escriba 'SALIR' para dejar de jugar");
            respuesta = sc.nextLine();
            if (respuesta.equalsIgnoreCase("NUEVA")) {
                opciones.add(preguntasRespuestas.get(i).get(1)); // meter en la lista opciones la respuesta correcta
                for (int j = 0; j < 2; j++) {
                    palabrasRandom = rand.nextInt(palabrasIncorrectas.size());
                    opciones.add(palabrasIncorrectas.get(palabrasRandom)); // meter en la lista opciones una respuesta incorrecta
                }

                sb.append("PREGUNTA: " + preguntasRespuestas.get(i).get(0) + " || OPCIONES: "); // PREGUNTA (palabra pregunta) OPCIONES

                for (int j = 0; j < 3; j++) { // bucle para aleatorizar el orden en el que sacamos las opciones
                    opcionesRandom = rand.nextInt(opciones.size());
                    sb.append(opciones.get(opcionesRandom) + " ");
                    opciones.remove(opcionesRandom);
                }
                System.out.println(sb.toString().trim()); // lo sacamos por pantalla y trimeamos para quitar el último espacio
                String respuesta2;

                do {
                    respuesta2 = sc.nextLine();
                    if (quitarTildes(respuesta2).equalsIgnoreCase(preguntasRespuestas.get(i).get(1))) { // si responde la respuesta correcta
                        System.out.println("OK");
                    } else if (respuesta2.equalsIgnoreCase("PISTA")) {
                        System.out.println("PISTA " + pistas.get(i));
                    } else {
                        System.out.println("ERROR");
                    }
                }
                while (respuesta2.equalsIgnoreCase("PISTA"));

            } else if (respuesta.equalsIgnoreCase("SALIR")) {
                i = preguntasRespuestas.size();
            } else {
                System.out.println("Respuesta no válida");
                i--;
            }
            if (i >= preguntasRespuestas.size() - 1) {
                System.out.println("Gracias por jugar");
            }
        }

    }


    public enum listadoPalabras {
        ORDENADOR, TECLADO, MALETA, OFICINA, CARPETA, PERSIANA, CRISTAL, PANTALON, PUNTO, ECUACION, SILENCIO, TEOREMA,
        LOGICA, DECADA, FILOSOFIA, MICROCHIP, ETERNIDAD, ALGORITMO, VOLTIO, ESTATUA, ICONO, ESFERA, TEORIA, PLEGARIA,
        ORDENANZA, HEPTAGONO, PARADOJA, INERCIA, SINTESIS, LIMITE, SARTEN, CORREO, CORTINA, LLAVE, TATUAJE, CUCHARA,
        BOLSILLO, ESPEJO, CITA, HORARIO, TIJERA, BOTON, RECIBO, ALMOHADA, GRAPA, POMO, CENICERO, CLAVO, HOMBRO, BARRA
    }

    public enum listadoPreguntas {
        SOL, DIA, MADERA, MESA, ANIMAL, PERRO, DEPORTE, TENIS, COLOR, AMARILLO, MUSICA,
        CANCION, AGUA, OCEANO, FRUTA, FRESA, FLOR, TULIPAN, PISTOLA, BALA
    }

    public static String quitarTildes(String input) {
        // 1. Normaliza la cadena a la forma NFD (Canonical Decomposition)
        // Esto separa las letras de sus diacríticos (acentos)
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        // á 'a

        // 2. Define el patrón para buscar caracteres diacríticos combinados
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        // 3. Reemplaza todos los diacríticos encontrados con una cadena vacía
        return pattern.matcher(normalizedString).replaceAll("");
    }
}
