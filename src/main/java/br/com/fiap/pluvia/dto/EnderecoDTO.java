package br.com.fiap.pluvia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnderecoDTO {

    private Integer idEndereco;

    @NotBlank(message = "Cidade não pode ser nula.")
    @JsonProperty("localidade")
    private String cidade;

    @NotBlank(message = "Bairro não pode ser nulo.")
    private String bairro;

    @NotBlank(message = "Logradouro não pode ser nulo.")
    private String logradouro;

    @NotBlank(message = "Sigla de estado não pode ser nula.")
    @Size(min = 2, max = 2, message = "A sigla do estado deve ter exatamente 2 caracteres.")
    private String uf;

    @NotNull(message = "CEP não pode ser nulo.")
    @Pattern(
            regexp = "^\\d{5}-?\\d{3}$",
            message = "CEP inválido. Formato válido: 00000-000 ou 00000000"
    )
    private String cep;

    private double latitude;

    private double longitude;
}
