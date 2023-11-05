# DSA-Minimo1
Todo funciona de manera adecuada, tanto la parte I como la parte II. 
Implementé todas las operaciones que se pedía siguiendo sus requisitos, aun así, expongo algunos detalles de los métodos (lo que devuelve) y clases que no se especificaban en el pdf o pequeños cambios que decidí hacer:
-	Creé las clases jugador, partida y juego.
-	La clase jugador genera un identificador random al añadir un nuevo jugador.
-	Creación de un juego: Devuelve el juego. Puede lanzar dos excepciones, una en el caso de que se ponga un nivel <1 y otra que no permita guardar dos juegos con el mismo identificador. 
-	Inicio de una partida por parte de un usuario: Devuelve la partida creada. Se registrará automáticamente la fecha actual. 
-	Consulta del nivel actual: Devuelve la partida en curso del usuario.
-	Consulta de la puntuación: Devuelve una frase con el número de puntos.
-	Pasar de nivel: Devuelve la partida con el nivel actualizado. La fecha del cambio de nivel se actualizará automáticamente con la fecha actual en vez de introducirla manualmente.
-	Finalizar partida: Devuelve una frase indicando que el usuario en cuestión finaliza la partida.
-	Consulta de usuarios que han participado en un juego ordenado por la puntuación (descendente): Devuelve una lista de las partidas de un juego ordenadas por puntuación de manera descendente. Consideré que era mejor devolver una lista de partidas ya que en ellas podemos ver diferente información interesante como el id de usuario, los puntos y el nivel. En cambio, si devolviese una lista de la clase jugador solo veríamos los identificadores de cada uno.
-	Consulta de las partidas en las que ha participado un usuario: Devuelve una lista de las partidas.
Como se pedía, a mayores implementé otras operaciones necesarias como puede ser la de añadir un jugador. Creé también diferentes clases para cada excepción que podría surgir, cada una con su correspondiente mensaje de error, como por ejemplo, UserNotFoundException (“El usuario no existe”) o JuegoNotFoundException. Todos los métodos contienen trazas de LOG4J explicando al inicio que se quiere hacer y los valores de los parámetros, así como trazas para indicar un error o el resultado obtenido. También implementé el test donde se pone a prueba todos los posibles resultados y con el que comprobé el correcto funcionamiento de 4 operaciones. 
Con respecto a la parte II, implementé un servicio REST que permite realizar todas las operaciones requeridas, teniendo en cuenta también los posibles errores (usuario no existe, el usuario no está en una partida, etc.). Al implementarlo me surgió un problema, entiendo que por como detectaba las clases que creé, me daba un error 500 ya que no era capaz de convertir un objeto de una clase a un formato JSON que pudiese ser devuelto en la respuesta a las diferentes operaciones. Para solucionar este problema lo que hice fue pasar los objectos de determinada clase (Juego o Partida) a string.

