package br.com.fiap.pluvia.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity(name = "usuario")
@Table(name = "T_PL_USUARIO")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq_gen")
    @SequenceGenerator(name = "usuario_seq_gen", sequenceName = "seq_usuario", allocationSize = 1)
    @NotNull
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;
    @ManyToOne
    @JoinColumn(name = "id_endereco", referencedColumnName = "ID_ENDERECO")
    private Endereco endereco;
    @NotBlank(message = "Nome não pode ser nulo.")
    @Column(name = "ID_NOME")
    private String nome;
    @NotBlank(message = "E-mail não pode ser nulo.")
    @Column(name = "ID_EMAIL")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "E-mail inválido."
    )
    private String email;
    @NotBlank(message = "CPF não pode ser nulo.")
    @Column(name = "CD_CPF")
    @Pattern(
            regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$",
            message = "CPF inválido. Use 11 dígitos, com ou sem formatação."
    )
    private String cpf;
    @NotBlank(message = "Senha não pode ser nula.")
    @Column(name = "CD_SENHA")
    @Pattern(
            regexp = "^.{6,}$",
            message = "Senha inválida. No mínimo 6 digitos."
    )
    private String senha;
}
