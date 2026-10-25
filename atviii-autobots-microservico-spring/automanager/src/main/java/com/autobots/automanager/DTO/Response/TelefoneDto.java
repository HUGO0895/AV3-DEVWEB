package com.autobots.automanager.DTO.Response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelefoneDto extends RepresentationModel<TelefoneDto> {
    private Long id;
    private String ddd;
    private String numero;
    private Long usuario;
    private Long empresa;
}
