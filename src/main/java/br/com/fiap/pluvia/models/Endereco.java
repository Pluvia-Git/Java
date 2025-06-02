package br.com.fiap.pluvia.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity(name = "endereco")
@Table(name = "T_PL_ENDERECO")
@Data
public class Endereco {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_seq")
    @SequenceGenerator(name = "endereco_seq", sequenceName = "SEQ_ENDERECO", allocationSize = 1)
    @Column(name = "ID_ENDERECO")
    private Integer idEndereco;
    @NotBlank( message = "Cidade não pode ser nula.")
    @Column(name = "ID_CIDADE")
    private String cidade;
    @NotBlank( message = "Bairro não pode ser nulo.")
    @Column(name = "ID_BAIRRO")
    private String bairro;
    @NotBlank( message = "Logradouro não pode ser nulo.")
    @Column(name = "DS_LOGRADOURO")
    private String logradouro;
    @NotBlank( message = "Sigla de estado não pode ser nula.")
    @Size(min = 2, max = 2, message = "A sigla do estado deve ter exatamente 2 caracteres.")
    @Column(name = "SG_ESTADO")
    private String uf;
    @NotNull(message = "CEP não pode ser nulo.")
    @Pattern(
            regexp = "^\\d{5}-?\\d{3}$",
            message = "CEP inválido. Formato válido: 00000-000 ou 00000000"
    )
    @Column(name = "CD_CEP")
    private String cep;
    @Column(name = "VL_LATITUDE")
    private Float latitude;
    @Column(name = "VL_LONGITUDE")
    private Float longitude;
}
