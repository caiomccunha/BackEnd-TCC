package TCC.Trabalho.TCC.V.de.Vigilancia.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.DemandasModel;

@Repository
public interface DemandasRepository extends JpaRepository<DemandasModel, Long>{

}
