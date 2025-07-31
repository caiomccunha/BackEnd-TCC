package TCC.Trabalho.TCC.V.de.Vigilancia.Model.Mensagem;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    
}
