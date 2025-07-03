package TCC.Trabalho.TCC.V.de.Vigilancia.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Usuario.UsuarioModel;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Usuario.tipoUsuario;

@DataJpaTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository repository;

    @Test
    public void salvarUsuario(){
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome ("Caio Messi");
        usuario.setEmail("caiomessi@gmail.com");
        usuario.setDocumento("12345678699");
        usuario.setCep("31585440");
        usuario.setCidade("Belo Horizonte");
        usuario.setEstado("Minas Gerais");
        usuario.setTelefone("31981144221");
        usuario.setTipo_usuario(tipoUsuario.PRODUTOR);
        usuario.setBiografia("Produtor de Arroz");

        UsuarioModel save = repository.save(usuario);

        assertNotNull(save);
        assertNotNull(save.getId(), "ID deve ser gerado após salvar");
        assertEquals("Caio Messi", save.getNome());
    }
}
