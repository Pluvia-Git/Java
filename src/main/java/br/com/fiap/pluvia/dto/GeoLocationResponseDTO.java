package br.com.fiap.pluvia.dto;

import lombok.Data;

@Data
public class GeoLocationResponseDTO {

    private String name;
    private double lat;
    private double lon;
    private String country;
    private String state;

}
