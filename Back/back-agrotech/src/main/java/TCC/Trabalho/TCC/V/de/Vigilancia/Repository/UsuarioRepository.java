package TCC.Trabalho.TCC.V.de.Vigilancia.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Usuario.UsuarioModel;

@Repository

public interface UsuarioRepository extends JpaRepository <UsuarioModel, Long> {

}
