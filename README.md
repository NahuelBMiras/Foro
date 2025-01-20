Claro, aquí tienes una versión modificada del README:

---

# Challenge Foro Hub - API REST

Bienvenido al proyecto **Challenge Foro Hub**, un **foro** desarrollado con **Java** y **Spring Boot**. Este sistema permite a los usuarios crear **tópicos**, plantear dudas o sugerencias y responder a otros dentro de la comunidad.

## 🚀 Características principales

- **Gestión de Tópicos**  
  Los usuarios pueden **crear**, **listar**, **buscar**, **actualizar** y **eliminar** tópicos. Además, es posible realizar búsquedas por palabras clave en el título o contenido de los tópicos.

- **Gestión de Respuestas**  
  Los usuarios pueden **asociar respuestas** a un tópico y **marcar como solución** las respuestas. Si un tópico tiene al menos una respuesta seleccionada como solución, su estado se cambia a **"Cerrado"**.

- **Autenticación y Seguridad**  
  La API utiliza **JWT** (JSON Web Tokens) para proteger los endpoints. Las contraseñas se almacenan de manera segura con **BCrypt**.

- **Documentación Interactiva**  
  Puedes probar la API directamente a través de la documentación generada por **Swagger** en el endpoint `/swagger-ui.html`.

- **Persistencia de Datos**  
  La aplicación utiliza **PostgreSQL** para almacenar los datos y **Flyway** para gestionar las migraciones de la base de datos.

## 🛠️ Tecnologías utilizadas

- **Lenguaje:** Java 
- **Frameworks:**
    - Spring Boot 3
    - Spring Data JPA
    - Spring Security
    - Spring Validation
- **Base de datos:** PostgreSQL
- **Herramientas adicionales:**
    - **Flyway**: para la gestión de migraciones de la base de datos
    - **Lombok**: para reducir el código repetitivo
    - **BCrypt**: para almacenar contraseñas de forma segura
    - **springdoc-openapi**: para generar la documentación de la API

## 🔧 Endpoints principales

### Tópicos
- **Listar todos los tópicos:** `GET /topicos`
- **Buscar un tópico por ID:** `GET /topicos/{id}`
- **Buscar tópicos por palabras clave:** `POST /topicos/busqueda`
- **Crear un nuevo tópico:** `POST /topicos`
- **Actualizar un tópico existente:** `PUT /topicos/{id}`
- **Eliminar un tópico:** `DELETE /topicos/{id}`

### Respuestas
- **Listar respuestas de un tópico:** `GET /topicos/{topicoId}/respuestas`
- **Crear una respuesta:** `POST /topicos/{topicoId}/respuestas`
- **Marcar respuesta como solución:** `PUT /topicos/{topicoId}/respuestas/{respuestaId}/solucion`
- **Eliminar respuesta:** `DELETE /topicos/{topicoId}/respuestas/{respuestaId}`

### Autenticación
- **Generar token JWT:** `POST /auth`

## 📝 Instalación y configuración

### 1. Clonar el repositorio

```bash
git clone <repositorio_url>
```

### 2. Configurar las variables de entorno

Agrega las siguientes variables en tu sistema o en un archivo `.env`:

```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/foro
DATABASE_USERNAME=tu_usuario
DATABASE_PASSWORD=tu_contraseña
JWT_SECRET=tu_secreto_para_jwt
```

### 3. Configurar la base de datos

Crea una base de datos PostgreSQL con el nombre definido en `DATABASE_URL`.

### 4. Ejecutar las migraciones

```bash
./mvnw flyway:migrate
```

### 5. Compilar y ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

### 6. Acceder a la documentación de la API

Visita [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) para interactuar con la API y probar los endpoints.

## 🧪 Pruebas

Para probar la API, puedes utilizar herramientas como **Insomnia** o **Postman**, o bien interactuar directamente con la documentación de Swagger.
