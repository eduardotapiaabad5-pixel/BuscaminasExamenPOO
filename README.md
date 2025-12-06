 Buscaminas - Examen de Programación Orientada a Objetos POO

Proyecto desarrollado como parte del examen final de la asignatura **Programación Orientada a Objetos**.  
Consiste en una implementación completa del clásico juego **Buscaminas**, utilizando:

- ✔ Programación Orientada a Objetos  
- ✔ Patrón de diseño **MVC**  
- ✔ Manejo de excepciones  
- ✔ Serialización de objetos (guardar/cargar partida)  
- ✔ Buenas prácticas de programación  
- ✔ Uso de GitHub y control de versiones  
# 🎯 Objetivo del Proyecto
Desarrollar una versión en consola del Buscaminas con un tablero de **10x10** casillas y **10 minas**, permitiendo:

- Jugar desde la consola
- Revelar casillas
- Marcar casillas sospechosas
- Detectar victoria o derrota
- Guardar y cargar partidas
- Separar la lógica en capas MVC
- Manejar errores y excepciones personalizadas

# Arquitectura del Proyecto

El proyecto está organizado en paquetes siguiendo el estándar MVC:

src/
├── examenpoo.app
├── examenpoo.control
├── examenpoo.modelo
├── examenpoo.vista
├── examenpoo.persistencia
└── examenpoo.excepciones

# Características Implementadas

# Tablero 10x10** con 10 minas generadas aleatoriamente  
# Primer movimiento seguro la primera casilla nunca es mina  
# Revelado en cascada  
# Marcado de casillas con bandera (F)  
# Victoria automática cuando se descubren todas las casillas seguras  
# Derrota al revelar una mina  
# Persistencia: guardar y cargar partidas con serialización  
# Excepciones personalizadas  
- `CasillaYaDescubiertaException`  
- `CoordenadaInvalidaException`

# InputParser 
Permite comandos flexibles:

- `A5` → Revelar A5  
- `M A5` → Marcar casilla  
- `SAVE partida.dat` → Guardar  
- `LOAD partida.dat` → Cargar  
- `EXIT` → Salir del juego  

# 🎮 Cómo Jugar
Al iniciar el juego verás un menú:

BUSCAMINAS  Consola 10x10 
Comandos:
A5 → Revelar casilla
M A5 → Marcar casilla
Save archivo → Guardar partida
Load archivo → Cargar partida
Exid → Salir


UML simplificado para mayor claridad


Tecnologías Utilizadas

- Java 8+  
- Eclipse IDE  
- Git & GitHub  
- Patrón MVC  
- Serialización  

Autores

Proyecto desarrollado por:  
Kevin Ivan Farinango Chico
Victor Eduardo Salgado Altafuya
Jose Eduardo Tapia Abad
Jose Armando Chiquito Alay

Repositorio

Puedes clonar el proyecto desde aquí:
https://github.com/eduardotapiaabad5-pixel/BuscaminasExamenPOO.git

Muchas gracias por ver nuestro proyecto.




