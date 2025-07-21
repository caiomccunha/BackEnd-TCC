package TCC.Trabalho.TCC.V.de.Vigilancia.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Usuarios.UsuarioCadastroDTO;
import TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Usuarios.UsuarioDTO;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Usuario.UsuarioModel;
import TCC.Trabalho.TCC.V.de.Vigilancia.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping ("tcc/usuarios")
@CrossOrigin (origins = "*")
@Tag(name = "Usuario", description = "Documentação do Crud de usuários do sistema")

public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @Operation(summary = "Lista de Usuarios", description = "Retorno da Lista de Usuarios")
    @GetMapping
    public List <UsuarioModel> buscarUsuario(){
        return service.buscarUsers();
    }
    @Operation(summary = "Busca de usuários por ID", description = "Retorno dos usuários do sistema, apenas pelo ID")   
    @GetMapping("/{id}")
    public ResponseEntity <UsuarioDTO> buscarUsuarioPorId(@PathVariable Long id){
        return service.buscarUserPorID(id).map(u -> ResponseEntity.ok(service.toDTO(u))).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Busca de usuários por Email", description = "Retorno dos usuários do sistema, apenas pelo Email")
    @GetMapping("/email/{email}")
    public ResponseEntity <UsuarioDTO> buscarUsuarioPorEmail(@PathVariable String email){
        return service.buscarUserPorEmail(email).map(u -> ResponseEntity.ok(service.toDTO(u))).orElse(ResponseEntity.notFound().build());
    }

    @Operation (summary = "Adicionar usuários", description = "Retorno da função de adicionar os usuários do sistema")
    @PostMapping
    public ResponseEntity <UsuarioDTO> adicionarUsuario(@RequestBody UsuarioCadastroDTO dto){
       try {
            UsuarioDTO usuarioSalvo = service.salvarUsuario(dto);
            return ResponseEntity.ok(usuarioSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
       }
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Autentica um usuário pelo email e senha")
    public ResponseEntity<?> login(@RequestBody UsuarioModel login) {
        Optional<UsuarioModel> usuario = service.autenticar(login.getEmail(), login.getSenha());

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }
    }

    @PostMapping("/{id}/foto")
    @Operation(summary = "Upload de foto de perfil", description = "Faz o upload da imagem de perfil do usuário")
    public ResponseEntity<?> uploadFoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
    try {
        Optional<UsuarioModel> usuarioOptional = service.buscarUserPorID(id);

        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UsuarioModel usuario = usuarioOptional.get();
        usuario.setFoto_perfil(file.getBytes());

        service.adicionarUser(usuario); // reusa o save

        return ResponseEntity.ok("Foto de perfil enviada com sucesso!");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao salvar a foto: " + e.getMessage());
    }

    }

    @Operation(summary = "Edição de usuários", description = "EndPoint da função de editar os usuários do sistema")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editarInformaçõesDeUsuario(@PathVariable Long id, @RequestBody UsuarioCadastroDTO dto){
        return service.atualizarUsuario(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar Usuários", description = "EndPoint da função delete no sistema de usuários")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        if (!service.buscarUserPorID(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.removerUser(id);
        return ResponseEntity.noContent().build();
        
    } 
   
    @Operation(summary = "Alerta de dados do usuário", description = "Exibe um alerta se o nome do usuário for 'Marcos'")
    @GetMapping("/alerta")
    public ResponseEntity<String> alertaSobreDadosUsuario(@RequestBody UsuarioModel usuario) {
        service.pegandoAlertandoSobreDados(usuario);
        return ResponseEntity.ok("Alerta processado, verifique o console para detalhes.");
    }
}
    
