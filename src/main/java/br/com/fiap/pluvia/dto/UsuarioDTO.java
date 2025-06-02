package br.com.fiap.pluvia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Integer idUsuario;

    private EnderecoDTO endereco;

    @NotBlank(message = "Nome não pode ser nulo.")
    private String nome;

    @NotBlank(message = "E-mail não pode ser nulo.")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "E-mail inválido."
    )
    private String email;

    @NotBlank(message = "CPF não pode ser nulo.")
    @Pattern(
            regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$",
            message = "CPF inválido. Use 11 dígitos, com ou sem formatação."
    )
    private String cpf;

    @NotBlank(message = "Senha não pode ser nula.")
    @Pattern(
            regexp = "^.{6,}$",
            message = "Senha inválida. No mínimo 6 dígitos."
    )
    private String senha;

}
