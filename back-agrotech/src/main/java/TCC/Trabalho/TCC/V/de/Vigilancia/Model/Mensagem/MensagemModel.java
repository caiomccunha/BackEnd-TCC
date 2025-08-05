package TCC.Trabalho.TCC.V.de.Vigilancia.Model.Mensagem;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Mensagem")
@Getter
@Setter
@NoArgsConstructor
public class MensagemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 2048)
    private String conteudo;

    @Column (nullable = false)
    private Long idRementente;

    @Column (nullable = false)
    private Long idDestinarario;

    @Column (nullable = false)
    private LocalDateTime data_envio;

    
}
