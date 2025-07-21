package TCC.Trabalho.TCC.V.de.Vigilancia.Model.Usuario;

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
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 200)
    private String nome;

    @Column (nullable = false, length = 200, unique = true)
    private String email;

    @Column (nullable = false, length = 20, unique = true)
    private String documento;

    @Column (nullable = false)
    private String cep;

    @Column (nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column (nullable = false)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private tipoUsuario tipo_usuario;

    @Enumerated(EnumType.STRING)
    private tipoApoiador tipo_apoiador;

    
    private String biografia;

    @Lob
    @Column (name = "foto_perfil", columnDefinition = "LONGBLOB")
    private byte[] foto_perfil;

    @Column(nullable = false)
    private String senha;
}
