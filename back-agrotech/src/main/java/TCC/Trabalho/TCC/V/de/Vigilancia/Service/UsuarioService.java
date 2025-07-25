package TCC.Trabalho.TCC.V.de.Vigilancia.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Usuario.UsuarioModel;
import TCC.Trabalho.TCC.V.de.Vigilancia.Repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public List<UsuarioModel> buscarUsers(){
        return repository.findAll();
    }

    public Optional <UsuarioModel> buscarUserPorID(Long id){
        return repository.findById(id);
    }

    public UsuarioModel adicionarUser(UsuarioModel usuario){
        return repository.save(usuario);
    }

    public void removerUser(Long id){
        repository.deleteById(id);
    }
}
