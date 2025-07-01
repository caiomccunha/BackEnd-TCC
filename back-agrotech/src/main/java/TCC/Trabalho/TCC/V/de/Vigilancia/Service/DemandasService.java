package TCC.Trabalho.TCC.V.de.Vigilancia.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.DemandasModel;
import TCC.Trabalho.TCC.V.de.Vigilancia.Repository.DemandasRepository;

@Service
public class DemandasService {
    @Autowired
    private DemandasRepository repository;

    public List<DemandasModel> buscarDemandasProd(){
        return repository.findAll();
    }

    public Optional <DemandasModel> buscarDemandaId(Long id){
        return repository.findById(id);
    }

    public DemandasModel cadastrarDemandar(DemandasModel demandaProdutor){
        return repository.save(demandaProdutor);
    }

    public void excluirDemanda(Long id){
         repository.deleteById(id);
    }


}
