package com.autobots.automanager.DTO.Response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDTO extends RepresentationModel<EmailDTO> {
    private Long id;
    private String endereco;
    private Long usuario;
}
