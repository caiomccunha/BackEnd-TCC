package TCC.Trabalho.TCC.V.de.Vigilancia.DTO.Demandas;

import java.sql.Date;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Demanda.Categoria_Demanda;
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
    private String cidade;
    private String estado;
    private Date validade_oferta;
}
