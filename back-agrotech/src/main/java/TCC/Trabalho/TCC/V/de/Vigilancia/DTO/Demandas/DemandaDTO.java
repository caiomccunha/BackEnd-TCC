package TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Demandas;

import java.sql.Date;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.Categoria_Demanda;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.statusDemanda;
import jakarta.persistence.Transient;
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

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime data_postagem;

    private Categoria_Demanda categoria;
    private statusDemanda status;
    private String cidade;
    private String estado;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validade_oferta;

    // Apenas o ID do usuário
    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuario;

    // Campos extras
    @Transient
    private String usuarioNome;

    @Transient
    private String usuarioFoto;

    @Transient
    private String usuarioTipo;
}
