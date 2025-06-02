package br.com.fiap.pluvia.client;

import br.com.fiap.pluvia.dto.EnderecoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public EnderecoDTO buscarEnderecoPorCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, EnderecoDTO.class);
    }


}
