package TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Demandas;

import java.sql.Date;
import java.time.LocalDate;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.Categoria_Demanda;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.TipoApoio;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.statusDemanda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriacaoDemandaDTO {
    private String titulo;
    private String descricao;
    private Categoria_Demanda categoria;
    private Date data_postagem;
    private String cidade;
    private String estado;
    private Date validade_oferta;
    private TipoApoio tipo_apoio; // Representa o tipo de apoio como String

    // Novos campos
    private Long usuarioId; // ID do usuário que cria a demanda
    private statusDemanda status; // Status inicial da demanda
}
