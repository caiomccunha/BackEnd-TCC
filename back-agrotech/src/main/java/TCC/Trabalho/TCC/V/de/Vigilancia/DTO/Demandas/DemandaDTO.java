package TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Demandas;

import java.sql.Date;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.Categoria_Demanda;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.statusDemanda;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Usuario.UsuarioModel;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DemandaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime data_postagem;
    
    private Categoria_Demanda categoria;
    private statusDemanda status;
    private String cidade;
    private String estado;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validade_oferta;

    // Campo para receber APENAS o ID do usuário no JSON
    @NotNull(message = "O ID do usuário é obrigatório")
    private UsuarioModel usuario;  // Nome diferente para evitar conflito com o JPA

    // Campos extras (opcional)
    private String usuarioNome;
    private String usuarioFoto;
    private String usuarioTipo;
}