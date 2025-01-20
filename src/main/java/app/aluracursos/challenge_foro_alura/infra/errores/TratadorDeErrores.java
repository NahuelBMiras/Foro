package app.aluracursos.challenge_foro_alura.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  // Este anotación hace que esta clase maneje las excepciones globalmente para los controladores.
public class TratadorDeErrores {

    // Método para manejar errores 404 (Entidad no encontrada).
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        // Devuelve una respuesta con estado HTTP 404 Not Found cuando no se encuentra una entidad.
        return ResponseEntity.notFound().build();
    }

    // Método para manejar errores 400 (Solicitud incorrecta, cuando no se cumplen las validaciones de los parámetros).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        // Obtiene todos los errores de campo de la excepción y los convierte en un formato adecuado para la respuesta.
        var errores = e.getFieldErrors().stream()
                        .map(DatosErrorValidacion::new)  // Mapea cada error de campo a un objeto DatosErrorValidacion.
                        .toList();
        // Devuelve una respuesta con estado HTTP 400 Bad Request junto con los errores de validación.
        return ResponseEntity.badRequest().body(errores);
    }

    // Método para manejar excepciones personalizadas de validación.
    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity tratarErrorDeValidacion(ValidacionException e){
        // Devuelve una respuesta con estado HTTP 400 Bad Request y el mensaje de la excepción.
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // Esta clase interna es utilizada para estructurar los errores de validación de manera legible.
    private record DatosErrorValidacion(
            String campo,  // Nombre del campo que falló la validación.
            String error   // El mensaje de error que describe lo que salió mal.
    ){
        // Constructor que toma un FieldError y lo mapea a un objeto DatosErrorValidacion.
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());  // Asigna los valores del error de campo.
        }
    }
}
