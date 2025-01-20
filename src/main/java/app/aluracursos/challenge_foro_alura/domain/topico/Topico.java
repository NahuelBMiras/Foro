package app.aluracursos.challenge_foro_alura.domain.topico;

import app.aluracursos.challenge_foro_alura.domain.respuesta.Respuesta;
import app.aluracursos.challenge_foro_alura.domain.usuario.Usuario;
import app.aluracursos.challenge_foro_alura.domain.usuario.UsuarioRepository;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;  // Título del tópico.
    private String mensaje; // Mensaje del tópico.
    private LocalDateTime fechaDeCreacion; // Fecha en la que se creó el tópico.
    private String status;  // Estado del tópico (Abierto o Cerrado).

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; // El usuario que creó el tópico.

    @Enumerated(EnumType.STRING)
    private Cursos curso;  // El curso relacionado con el tópico (como si fuera una categoría).

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Respuesta> respuestas = new ArrayList<>(); // Lista de respuestas asociadas al tópico.

    // Constructor que crea un nuevo tópico con los datos proporcionados.
    public Topico(DatosTopicoNuevo datos, UsuarioRepository usuarioRepository) {
        this.titulo = datos.titulo(); // Asigna el título del tópico.
        this.mensaje = datos.mensaje(); // Asigna el mensaje del tópico.
        this.fechaDeCreacion = LocalDateTime.now(); // La fecha de creación se asigna automáticamente.
        this.status = "Abierto"; // Inicialmente, el tópico está abierto.
        this.usuario = usuarioRepository.findById(datos.usuarioId()) // Se busca al usuario que creó el tópico.
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + datos.usuarioId()));
        this.curso = datos.curso(); // Se asigna el curso relacionado al tópico.
    }

    // Método que permite actualizar el título y mensaje del tópico.
    public void actualizar(@Valid DatosActualizarTopico datosActualizar) {
        if (datosActualizar.titulo() != null) {
            this.titulo = datosActualizar.titulo(); // Si el título es proporcionado, se actualiza.
        }
        if (datosActualizar.mensaje() != null) {
            this.mensaje = datosActualizar.mensaje(); // Si el mensaje es proporcionado, se actualiza.
        }
    }

    // Método que marca el tópico como cerrado si se ha solucionado.
    public void marcarComoCerrado(Boolean solucion) {
        if (solucion) {
            this.status = "Cerrado"; // Si se ha encontrado una solución, el estado del tópico se cambia a "Cerrado".
        }
    }

    // Sobrescritura del método toString para representar al tópico de forma más legible.
    @Override
    public String toString() {
        return "Topico{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", fechaDeCreacion=" + fechaDeCreacion +
                ", status=" + status +
                ", usuario=" + usuario.getNombre() + // Muestra el nombre del usuario que creó el tópico.
                ", curso=" + curso + // Muestra el curso relacionado.
                '}';
    }
}
