package TCC.Trabalho.TCC.V.de.Vigilancia.Controller;

import java.util.List;

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

@RestController
@RequestMapping ("tcc/usuarios")
@CrossOrigin (origins = "*")

public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public List <UsuarioModel> buscarUsuario(){
        return service.buscarUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity <UsuarioModel> buscarUsuarioPorId(@PathVariable Long id){
        return service.buscarUserPorID(id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UsuarioModel adicionarUsuario (@RequestBody UsuarioModel usuario){
        return service.adicionarUser(usuario);
    }

    @PutMapping ("/{id}")
    public ResponseEntity <UsuarioModel> editarInformaçõesDeUsuario (@PathVariable Long id, @RequestBody UsuarioModel usuario){
        if (!service.buscarUserPorID(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        usuario.setId(id);
        return ResponseEntity.ok(service.adicionarUser(usuario));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity <Void> deletarUsuario (@PathVariable Long id){
        if (!service.buscarUserPorID(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.removerUser(id);
        return ResponseEntity.noContent().build();
    }
}   
