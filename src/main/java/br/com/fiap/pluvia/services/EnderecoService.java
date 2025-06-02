package br.com.fiap.pluvia.services;

import br.com.fiap.pluvia.client.GeoApiClient;
import br.com.fiap.pluvia.client.ViaCepClient;
import br.com.fiap.pluvia.dto.GeoLocationResponseDTO;
import br.com.fiap.pluvia.dto.EnderecoDTO;
import br.com.fiap.pluvia.models.Endereco;
import br.com.fiap.pluvia.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ViaCepClient viaCepClient;
    private final GeoApiClient geoClient;

    public Endereco buscarOuCriarPorCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new IllegalArgumentException("CEP não pode ser nulo ou vazio");
        }

        return enderecoRepository.findByCep(cep)
                .orElseGet(() -> criarNovoEndereco(cep));
    }

    private Endereco criarNovoEndereco(String cep) {
        EnderecoDTO viaCep = viaCepClient.buscarEnderecoPorCep(cep);

        validarDadosViaCep(viaCep);

        try {
            GeoLocationResponseDTO geo = geoClient.buscaLatitudeLongitude(
                    viaCep.getCidade(),
                    viaCep.getUf(),
                    "BR"
            );

            Endereco endereco = new Endereco();
            endereco.setCep(cep);
            endereco.setLogradouro(viaCep.getLogradouro());
            endereco.setCidade(viaCep.getCidade());
            endereco.setUf(viaCep.getUf());
            endereco.setLatitude((float) geo.getLat());
            endereco.setLongitude((float) geo.getLon());

            log.debug("Criando novo endereço: {}", endereco);

            return enderecoRepository.save(endereco);
        } catch (Exception e) {
            log.error("Erro ao processar geolocalização para CEP: {}", cep, e);
            throw new RuntimeException("Não foi possível obter a geolocalização para o endereço", e);
        }
    }

    private void validarDadosViaCep(EnderecoDTO viaCep) {
        if (viaCep == null) {
            throw new RuntimeException("Não foi possível obter dados do CEP");
        }
        if (viaCep.getCidade() == null || viaCep.getCidade().trim().isEmpty()) {
            throw new RuntimeException("Cidade não encontrada para o CEP informado");
        }
        if (viaCep.getUf() == null || viaCep.getUf().trim().isEmpty()) {
            throw new RuntimeException("UF não encontrada para o CEP informado");
        }
    }
}