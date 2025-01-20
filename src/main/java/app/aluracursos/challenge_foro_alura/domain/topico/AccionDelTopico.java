package app.aluracursos.challenge_foro_alura.domain.topico;

import app.aluracursos.challenge_foro_alura.domain.respuesta.RespuestaRepository;
import app.aluracursos.challenge_foro_alura.domain.usuario.UsuarioRepository;
import app.aluracursos.challenge_foro_alura.infra.errores.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  // Marca esta clase como un servicio para la inyección de dependencias
public class AccionDelTopico {

    @Autowired
    private UsuarioRepository uRepository;

    @Autowired
    private TopicoRepository tRepository;

    @Autowired
    private RespuestaRepository rRepository;

    // Método para crear un nuevo tópico
    public DatosConfirmacionPostTopico postearTopico(DatosTopicoNuevo datosTopicoNuevo) {
        // Verificar si el usuario existe
        if (!uRepository.existsById(datosTopicoNuevo.usuarioId())) {
            throw new ValidacionException("El id de usuario ingresado no existe.");
        }

        // Verificar si ya existe un tópico con el mismo título
        if (tRepository.existsByTitulo(datosTopicoNuevo.titulo())) {
            throw new ValidacionException("Un tópico con el título ingresado ya existe.");
        }

        // Crear el tópico con los datos proporcionados
        var topico = new Topico(datosTopicoNuevo, uRepository);
        tRepository.save(topico);

        // Retornar la confirmación con el tópico creado
        return new DatosConfirmacionPostTopico(topico);
    }

    // Método para actualizar un tópico
    public DatosConfirmacionPostTopico actualizarTopico(Long id, DatosActualizarTopico datosActualizar) {
        // Verificar si el tópico existe
        if (!tRepository.existsById(id)) {
            throw new ValidacionException("El id de tópico ingresado no existe.");
        }

        // Verificar si el usuario existe
        if (!uRepository.existsById(datosActualizar.usuarioId())) {
            throw new ValidacionException("El id de usuario ingresado no existe.");
        }

        // Verificar si el usuario es el propietario del tópico
        if (!tRepository.getReferenceById(id).getUsuario().getId().equals(datosActualizar.usuarioId())) {
            throw new ValidacionException("Su id de usuario no tiene autorización para modificar este post.");
        }

        // Verificar si el título o mensaje del tópico ya existe
        if (tRepository.existsByTitulo(datosActualizar.titulo())) {
            if (tRepository.getReferenceById(id).getTitulo().equals(datosActualizar.titulo())) {
                throw new ValidacionException("El título actualizado es igual al título original.");
            } else {
                throw new ValidacionException("Un tópico con el título ingresado ya existe.");
            }
        }

        if (tRepository.existsByMensaje(datosActualizar.mensaje())) {
            if (tRepository.getReferenceById(id).getMensaje().equals(datosActualizar.mensaje())) {
                throw new ValidacionException("El mensaje actualizado es igual al mensaje original.");
            } else {
                throw new ValidacionException("Un tópico con el mensaje ingresado ya existe.");
            }
        }

        // Actualizar el tópico con los nuevos datos
        Topico topico = tRepository.getReferenceById(id);
        topico.actualizar(datosActualizar);

        // Retornar la confirmación con el tópico actualizado
        return new DatosConfirmacionPostTopico(topico);
    }

    // Método para eliminar un tópico
    public void eliminarTopico(Long id) {
        // Verificar si el tópico existe
        if (!tRepository.existsById(id)) {
            throw new ValidacionException("El id de tópico ingresado no existe.");
        }

        // Eliminar el tópico
        tRepository.deleteById(id);
    }

    // Método para obtener un tópico por su ID
    public DatosCompletosTopico topicoPorId(Long id) {
        // Verificar si el tópico existe
        if (!tRepository.existsById(id)) {
            throw new ValidacionException("El id de tópico ingresado no existe.");
        }

        // Retornar los detalles completos del tópico
        return new DatosCompletosTopico(tRepository.getReferenceById(id), uRepository, rRepository);
    }
}
