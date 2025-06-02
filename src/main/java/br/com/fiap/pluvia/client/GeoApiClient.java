package br.com.fiap.pluvia.client;

import br.com.fiap.pluvia.dto.GeoLocationResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoApiClient {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GeoApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeoLocationResponseDTO buscaLatitudeLongitude(String cidade, String estado, String pais) {

        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("A cidade não pode ser nula ou vazia.");
        }

        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("O estado não pode ser nulo ou vazio.");
        }

        if (pais == null || pais.trim().isEmpty()) {
            throw new IllegalArgumentException("O país não pode ser nulo ou vazio.");
        }

        String url = String.format(
                "http://api.openweathermap.org/geo/1.0/direct?q=%s,%s,%s&limit=1&appid=%s",
                cidade, estado, pais, apiKey
        );

        try {
            GeoLocationResponseDTO[] response = restTemplate.getForObject(url, GeoLocationResponseDTO[].class);

            if (response != null && response.length > 0) {
                return response[0];
            } else {
                throw new RuntimeException(String.format(
                        "A API retornou resposta vazia para a localização: cidade=%s, estado=%s, pais=%s",
                        cidade, estado, pais));
            }
        } catch (HttpClientErrorException e) {
            // Erros 4xx
            String msg = String.format("Erro de cliente ao chamar API (%d): %s. URL: %s",
                    e.getStatusCode().value(), e.getStatusText(), url);
            throw new RuntimeException(msg, e);
        } catch (HttpServerErrorException e) {
            // Erros 5xx
            String msg = String.format("Erro de servidor ao chamar API (%d): %s. URL: %s",
                    e.getStatusCode().value(), e.getStatusText(), url);
            throw new RuntimeException(msg, e);
        } catch (ResourceAccessException e) {
            // Problemas de conexão, timeout, etc
            String msg = String.format("Erro de acesso ao recurso: %s. URL: %s", e.getMessage(), url);
            throw new RuntimeException(msg, e);
        } catch (Exception e) {
            // Qualquer outro erro inesperado
            String msg = String.format("Erro inesperado ao buscar latitude e longitude: %s. URL: %s",
                    e.getMessage(), url);
            throw new RuntimeException(msg, e);
        }
    }
}
