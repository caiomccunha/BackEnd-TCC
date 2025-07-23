package TCC.Trabalho.TCC.V.de.Vigilancia.Controller;

import TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Demandas.CriacaoDemandaDTO;
import TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Demandas.DemandaDTO;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.DemandasModel;
import TCC.Trabalho.TCC.V.de.Vigilancia.Service.DemandasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("tcc/demandas")
public class DemandasController { // Alterado para começar com letra maiúscula

    private final DemandasService service;

    @Autowired
    public DemandasController(DemandasService service) {
        this.service = service;
    }

    @GetMapping
    public List<DemandaDTO> buscarTodasDemandas() {
        return service.buscarDemandasProd().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemandaDTO> buscarPorID(@PathVariable Long id) {
        return service.buscarDemandaId(id)
            .map(this::converterParaDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DemandaDTO adicionarDemanda(@RequestBody CriacaoDemandaDTO demandaDTO) {
        DemandasModel demanda = converterParaModel(demandaDTO);
        return converterParaDTO(service.cadastrarDemandar(demanda));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DemandaDTO> editarDemanda(
            @PathVariable Long id, 
            @RequestBody CriacaoDemandaDTO demandaDTO) {
        
        if (!service.buscarDemandaId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        DemandasModel demanda = converterParaModel(demandaDTO);
        demanda.setId(id);
        return ResponseEntity.ok(converterParaDTO(service.cadastrarDemandar(demanda)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDemanda(@PathVariable Long id) {
        if (!service.buscarDemandaId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.excluirDemanda(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos auxiliares de conversão
    private DemandaDTO converterParaDTO(DemandasModel model) {
        DemandaDTO dto = new DemandaDTO();
        dto.setId(model.getId());
        dto.setIdUsuario(model.getIdUsuario().getId());
        dto.setTitulo(model.getTitulo());
        dto.setDescricao(model.getDescricao());
        dto.setData_postagem(model.getData_postagem());
        dto.setCategoria(model.getCategoria());
        dto.setStatus(model.getStatus());
        dto.setCidade(model.getCidade());
        dto.setEstado(model.getEstado());
        dto.setValidade_oferta(model.getValidade_oferta());
        
        if (model.getIdUsuario() != null) {
            dto.setUsuarioNome(model.getIdUsuario().getNome());
            dto.setUsuarioTipo(model.getIdUsuario().getTipo_usuario().toString());
        }
        
        return dto;
    }

    private DemandasModel converterParaModel(CriacaoDemandaDTO dto) {
        DemandasModel model = new DemandasModel();
        model.setTitulo(dto.getTitulo());
        model.setDescricao(dto.getDescricao());
        model.setCategoria(dto.getCategoria());
        model.setCidade(dto.getCidade());
        model.setEstado(dto.getEstado());
        model.setValidade_oferta(dto.getValidade_oferta());
        return model;
    }
}