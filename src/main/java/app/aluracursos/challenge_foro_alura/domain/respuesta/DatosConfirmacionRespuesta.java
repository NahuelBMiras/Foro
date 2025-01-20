package app.aluracursos.challenge_foro_alura.domain.respuesta;

import app.aluracursos.challenge_foro_alura.domain.topico.TopicoRepository;
import app.aluracursos.challenge_foro_alura.domain.usuario.UsuarioRepository;

import java.time.LocalDateTime;

// Registro que contiene la información de confirmación de la respuesta
public record DatosConfirmacionRespuesta(
        Long id,                // ID de la respuesta
        String nombreUsuario,   // Nombre del usuario que publicó la respuesta
        String tituloTopico,    // Título del tópico al que pertenece la respuesta
        String mensaje,         // Mensaje de la respuesta
        LocalDateTime fechaDeCreacion // Fecha de creación de la respuesta
) {
    // Constructor personalizado para convertir una Respuesta en un DTO con información adicional
    public DatosConfirmacionRespuesta(Respuesta respuesta, RespuestaRepository repository,
                                      UsuarioRepository uRepository, TopicoRepository tRepository) {
        this(respuesta.getId(),
             uRepository.getReferenceById(repository.getReferenceById(respuesta.getId()).getUsuario().getId()).getNombre(), 
             tRepository.getReferenceById(repository.getReferenceById(respuesta.getId()).getTopico().getId()).getTitulo(),
             respuesta.getMensaje(),
             respuesta.getFechaDeCreacion());
    }

    // Métodos getter sobreescritos (aunque al ser un record no son estrictamente necesarios)
    @Override
    public Long id() {
        return id;
    }

    @Override
    public String nombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public String tituloTopico() {
        return tituloTopico;
    }

    @Override
    public String mensaje() {
        return mensaje;
    }

    @Override
    public LocalDateTime fechaDeCreacion() {
        return fechaDeCreacion;
    }
}
