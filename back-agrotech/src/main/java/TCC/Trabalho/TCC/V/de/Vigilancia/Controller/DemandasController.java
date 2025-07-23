package TCC.Trabalho.TCC.V.de.Vigilancia.Controller;

import TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Demandas.CriacaoDemandaDTO;
import TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Demandas.DemandaDTO;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.DemandasModel;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Usuario.UsuarioModel;
import TCC.Trabalho.TCC.V.de.Vigilancia.Repository.UsuarioRepository;
import TCC.Trabalho.TCC.V.de.Vigilancia.Service.DemandasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tcc/demandas")
public class DemandasController {

    private final DemandasService demandasService;
    private final UsuarioRepository usuarioRepository;

    public DemandasController(DemandasService demandasService, UsuarioRepository usuarioRepository) {
        this.demandasService = demandasService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<DemandaDTO> buscarTodasDemandas() {
        return demandasService.buscarDemandasProd().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemandaDTO> buscarPorID(@PathVariable Long id) {
        return demandasService.buscarDemandaId(id)
                .map(this::converterParaDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DemandasModel> criarDemanda(@RequestBody DemandaDTO demandaDTO) {
        // Validação do usuário
        if (demandaDTO.getUsuario() == null) {
            throw new IllegalArgumentException("ID do usuário é obrigatório");
        }

        UsuarioModel usuario = usuarioRepository.findById(demandaDTO.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Conversão para Model
        DemandasModel demanda = new DemandasModel();
        demanda.setTitulo(demandaDTO.getTitulo());
        demanda.setDescricao(demandaDTO.getDescricao());
        demanda.setData_postagem(demandaDTO.getData_postagem());
        demanda.setCategoria(demandaDTO.getCategoria());
        demanda.setStatus(demandaDTO.getStatus());
        demanda.setCidade(demandaDTO.getCidade());
        demanda.setEstado(demandaDTO.getEstado());
        demanda.setValidade_oferta(demandaDTO.getValidade_oferta());
        demanda.setUsuario(usuario);

        DemandasModel demandaSalva = demandasService.cadastrarDemandar(demanda);
        return ResponseEntity.ok(demandaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DemandaDTO> editarDemanda(
            @PathVariable Long id,
            @RequestBody CriacaoDemandaDTO demandaDTO) {

        if (!demandasService.buscarDemandaId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        DemandasModel demanda = converterParaModel(demandaDTO);
        demanda.setId(id);
        return ResponseEntity.ok(converterParaDTO(demandasService.cadastrarDemandar(demanda)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDemanda(@PathVariable Long id) {
        if (!demandasService.buscarDemandaId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        demandasService.excluirDemanda(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos auxiliares de conversão
    private DemandaDTO converterParaDTO(DemandasModel model) {
        DemandaDTO dto = new DemandaDTO();
        dto.setId(model.getId());
        dto.setUsuario(model.getUsuario().getId());
        dto.setTitulo(model.getTitulo());
        dto.setDescricao(model.getDescricao());
        dto.setData_postagem(model.getData_postagem());
        dto.setCategoria(model.getCategoria());
        dto.setStatus(model.getStatus());
        dto.setCidade(model.getCidade());
        dto.setEstado(model.getEstado());
        dto.setValidade_oferta(model.getValidade_oferta());
        
        if (model.getUsuario() != null) {
            dto.setUsuarioNome(model.getUsuario().getNome());
            dto.setUsuarioTipo(model.getUsuario().getTipo_usuario().toString());
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