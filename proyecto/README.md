# Proyecto de Gestión de Obras

Este proyecto es una aplicación web para la gestión de obras literarias, capítulos y partes. Está dividido en dos secciones principales: un backend desarrollado en Java utilizando Spring Boot y un frontend construido con HTML, CSS y JavaScript.

## Estructura del Proyecto

```
proyecto
├── backend
│   ├── models
│   │   ├── Obra.java
│   │   ├── Capitulo.java
│   │   └── Parte.java
│   ├── repositories
│   │   └── ObraRepository.java
│   ├── controllers
│   │   └── ObraController.java
│   ├── services
│   │   └── ObraService.java
│   └── application.properties
├── frontend
│   ├── index.html
│   ├── obras.html
│   ├── estilos.css
│   └── scripts.js
└── README.md
```

## Backend

El backend está construido con Spring Boot y se encarga de manejar la lógica de negocio y las operaciones de la base de datos. 

### Modelos

- **Obra**: Representa una obra literaria con propiedades como `id`, `titulo` y `descripcion`.
- **Capitulo**: Representa un capítulo de una obra, con propiedades como `id`, `titulo`, `numero` y una referencia a `Obra`.
- **Parte**: Representa una parte de un capítulo, con propiedades como `id`, `titulo`, `numero` y una referencia a `Capitulo`.

### Repositorios

- **ObraRepository**: Interfaz que extiende `JpaRepository` para realizar operaciones CRUD sobre entidades `Obra`.

### Controladores

- **ObraController**: Maneja las peticiones HTTP relacionadas con `Obra`, incluyendo métodos para obtener, crear y actualizar obras.

### Servicios

- **ObraService**: Contiene la lógica de negocio para gestionar `Obra`, incluyendo métodos para buscar y guardar obras.

### Configuración

- **application.properties**: Contiene la configuración de conexión a la base de datos.

## Frontend

El frontend es una aplicación web que permite a los usuarios interactuar con el sistema.

### Archivos

- **index.html**: Archivo principal que incluye enlaces a los archivos CSS y JS.
- **obras.html**: Muestra una lista de obras y permite la navegación a otras páginas.
- **estilos.css**: Define los estilos visuales de la aplicación.
- **scripts.js**: Maneja la interactividad y las solicitudes HTTP al backend.

## Instrucciones de Configuración

1. Clona el repositorio.
2. Configura la base de datos en `backend/application.properties`.
3. Ejecuta el backend utilizando Spring Boot.
4. Abre `frontend/index.html` en un navegador para acceder a la aplicación.

## Uso

La aplicación permite gestionar obras literarias, visualizar capítulos y partes, y realizar operaciones CRUD a través de una interfaz web intuitiva.

## Contribuciones

Las contribuciones son bienvenidas. Si deseas colaborar, por favor abre un issue o envía un pull request.