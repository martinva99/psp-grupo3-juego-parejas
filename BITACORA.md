# PSP : UT3 Juegos [ PAREJAS ]
### 20/01/2026  

Creación del Trello  
Creación del Github  
Creación de la estructura "básica" del proyecto  
Investigación de trabajos relacionados con el proyecto.
Creación de la estructura básica de Servidor, Cliente y Handler.
Creación del listado de palabras incorrectas y de preguntas y respuestas.

### 22/01/2026

Creación del listado de pistas
Elección de cómo se va a implementar la lógica del juego.
Creación de una versión del juego aislada, poco optimizada, pero funcional.


Elección sobre el funcionamiento del código (formato tarjetas y formato resultados)
Primera versión de la lógica del juego terminada

Lógica del juego testeada
Primera versión del POJO

Para optimizar la generación de cuestionarios aleatorios, 
se emplean dos HashMaps: uno que vincula cada pregunta con su respuesta correcta 
y otro que actúa como un repositorio de términos incorrectos o distractores. 
El proceso consiste en seleccionar una pregunta y 
su respuesta vinculada del primer mapa para luego 
complementarlas con dos palabras elegidas al azar del segundo, 
agrupando finalmente estos cuatro elementos en un array 
que conforma la estructura completa de la tarjeta,
lista para ser mostrada al usuario con opciones dinámicas y variadas.

Además se añadira otro HashMap en el cual se guardan las pistas con el ID de cada pregunta
para relacionar cada pregunta con su pista personalizada.

El funcionamiento del programa es el siguiente: Se muestra una palabra,
y tres opciones a elegir como respuesta, una de estas esta es correcta  
y las otras dos son aleatorizadas y son incorrectas. Si el usuario, en lugar 
de escribir una de las respuestas escribe PISTA, se le mostrará la pista asociada a esa pregunta.