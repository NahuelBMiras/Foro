package app.aluracursos.challenge_foro_alura.domain.respuesta;

import app.aluracursos.challenge_foro_alura.domain.topico.Topico;
import app.aluracursos.challenge_foro_alura.domain.topico.TopicoRepository;
import app.aluracursos.challenge_foro_alura.domain.usuario.Usuario;
import app.aluracursos.challenge_foro_alura.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// Entidad Respuesta que representa las respuestas de los usuarios en los tópicos
@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")  // Compara las respuestas por su ID para operaciones de comparación
@SecurityRequirement(name = "bearer-key")  // Seguridad usando bearer token
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Generación automática del ID
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Relación con Usuario (muchas respuestas pueden tener un usuario)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)  // Relación con Topico (muchas respuestas pueden estar asociadas a un tópico)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    private String mensaje;

    private LocalDateTime fechaDeCreacion;  // Fecha de creación de la respuesta

    private Boolean solucion;  // Indica si la respuesta es la solución al problema

    // Constructor para crear una nueva respuesta a partir de los datos enviados
    public Respuesta(@Valid DatosNuevaRespuesta datos, UsuarioRepository uRepository, TopicoRepository tRepository) {
        // Validar y obtener el usuario y tópico desde los repositorios
        this.usuario = uRepository.findById(datos.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + datos.usuarioId()));
        this.topico = tRepository.findById(datos.topicoId())
                .orElseThrow(() -> new IllegalArgumentException("Tópico no encontrado con el ID: " + datos.topicoId()));

        // Asignar el nombre del usuario que creó la respuesta
        this.nombre = uRepository.getReferenceById(datos.usuarioId()).getNombre();

        // Asignar el mensaje de la respuesta
        this.mensaje = datos.mensaje();
        
        // Establecer la fecha de creación con la fecha actual
        this.fechaDeCreacion = LocalDateTime.now();
        
        // Inicializar la respuesta como no siendo una solución
        this.solucion = false;
    }

    // Método para marcar la respuesta como solución
    public void actualizarSolucion() {
        this.solucion = true;
    }
}
