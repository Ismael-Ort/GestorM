# GestorM

Proyecto web en Java con Spring Boot y Gradle que permite gestionar series, animes y películas.

## Ejecutar localmente

1. **Compilar y correr:**
   ```bash
   ./gradlew bootRun
   ```
   (En Windows usar `gradlew.bat`)
2. Abrir [http://localhost:8080](http://localhost:8080) para usar la interfaz web.
3. Consola H2 disponible en [http://localhost:8080/h2-console](http://localhost:8080/h2-console).

## Estructura de la base de datos

Se incluye el archivo `schema.sql` con un script para crear las tablas en caso de usar SQLite u otro motor de base de datos.
