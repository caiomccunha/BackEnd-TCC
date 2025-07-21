package TCC.Trabalho.TCC.V.de.Vigilancia.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Usuarios.UsuarioCadastroDTO;
import TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Usuarios.UsuarioDTO;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Usuario.UsuarioModel;
import TCC.Trabalho.TCC.V.de.Vigilancia.Repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioDTO salvarUsuario(UsuarioCadastroDTO dto) {

        // Verifica se o email já está cadastrado
        Optional<UsuarioModel> usuarioExistente = repository.findByEmail(dto.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        UsuarioModel model = new UsuarioModel();
        model.setNome(dto.getNome());
        model.setEmail(dto.getEmail());
        model.setDocumento(dto.getDocumento());
        model.setCep(dto.getCep());
        model.setCidade(dto.getCidade());
        model.setEstado(dto.getEstado());
        model.setTelefone(dto.getTelefone());
        model.setTipo_usuario(dto.getTipo_usuario());
        model.setTipo_apoiador(dto.getTipo_apoiador());
        model.setBiografia(dto.getBiografia());
        model.setSenha(dto.getSenha());
        model.setFoto_perfil(dto.getFoto_perfil()); // pode ser null

        return toDTO(repository.save(model));
    }

    public List<UsuarioModel> buscarUsers() {
        return repository.findAll();
    }

    public Optional<UsuarioModel> buscarUserPorID(Long id) {
        return repository.findById(id);
    }

    public Optional<UsuarioModel> buscarUserPorEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<UsuarioModel> autenticar(String email, String senha) {
        Optional<UsuarioModel> usuario = repository.findByEmail(email);
        if (usuario.isPresent() && usuario.get().getSenha().equals(senha)) {
            return usuario;
        }
        return Optional.empty();
    }

    public Optional<UsuarioDTO> atualizarUsuario(Long id, UsuarioCadastroDTO dto) {
        Optional<UsuarioModel> usuarioExistente = repository.findById(id);
        if (usuarioExistente.isPresent()) {
            UsuarioModel usuario = usuarioExistente.get();

            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setDocumento(dto.getDocumento());
            usuario.setCep(dto.getCep());
            usuario.setCidade(dto.getCidade());
            usuario.setEstado(dto.getEstado());
            usuario.setTelefone(dto.getTelefone());
            usuario.setTipo_usuario(dto.getTipo_usuario());
            usuario.setTipo_apoiador(dto.getTipo_apoiador());
            usuario.setBiografia(dto.getBiografia());

            if (dto.getFoto_perfil() == null) {
                try (InputStream input = getClass().getResourceAsStream("/static/icon perfil criado recentemente.png")) {
                    if (input != null) {
                        byte[] defaultImage = input.readAllBytes();
                        usuario.setFoto_perfil(defaultImage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                usuario.setFoto_perfil(dto.getFoto_perfil());
            }

            if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
                usuario.setSenha(dto.getSenha());
            } // se a senha for null, mantém a atual

            UsuarioModel usuarioAtualizado = repository.save(usuario);
            return Optional.of(toDTO(usuarioAtualizado));
        }
        return Optional.empty();
    }

    public UsuarioModel adicionarUser(UsuarioModel usuario) {
        return repository.save(usuario);
    }

    public void removerUser(Long id) {
        repository.deleteById(id);
    }

    public UsuarioModel pegandoAlertandoSobreDados(UsuarioModel usuario) {
        if ("Marcos".equals(usuario.getNome())) {
            System.out.println("Alerta: O usuário " + usuario.getNome() + " está cadastrado no sistema.");
        } else {
            System.out.println("Usuário " + usuario.getNome() + " não é o Marcos, sem alerta.");
        }
        return usuario;
    }

    public UsuarioDTO toDTO(UsuarioModel usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getDocumento(),
            usuario.getCep(),
            usuario.getCidade(),
            usuario.getEstado(),
            usuario.getTelefone(),
            usuario.getTipo_usuario(),
            usuario.getTipo_apoiador(),
            usuario.getBiografia(),
            usuario.getFoto_perfil()
        );
    }
}
