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
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity <UsuarioModel> buscarUsuarioPorId(@PathVariable Long id){
        return service.buscarUserPorID(id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation (summary = "Adicionar usuários", description = "Retorno da função de adicionar os usuários do sistema")
    @PostMapping
    public UsuarioModel adicionarUsuario (@RequestBody UsuarioModel usuario){
        return service.adicionarUser(usuario);
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


    @Operation(summary = "Edição de usuários" , description = "EndPoint da função de editar os usuários do sistema")
    @PutMapping ("/{id}")
    public ResponseEntity <UsuarioModel> editarInformaçõesDeUsuario (@PathVariable Long id, @RequestBody UsuarioModel usuario){
        if (!service.buscarUserPorID(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        usuario.setId(id);
        return ResponseEntity.ok(service.adicionarUser(usuario));
    }

    @Operation(summary = "Deletar Usuários", description = "EndPoint da função delete no sistema de usuários")
    @DeleteMapping ("/{id}")
    public ResponseEntity <Void> deletarUsuario (@PathVariable Long id){
        if (!service.buscarUserPorID(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.removerUser(id);
        return ResponseEntity.noContent().build();
    }
}   
