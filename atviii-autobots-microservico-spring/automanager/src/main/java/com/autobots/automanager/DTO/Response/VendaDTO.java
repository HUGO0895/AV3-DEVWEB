package com.autobots.automanager.DTO.Response;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDTO extends RepresentationModel<VendaDTO> {
    private Long id;
    private Date cadastro;
    private String identificacao;
    private Long empresa;
    private Long cliente;
    private Long funcionario;
    private Long veiculo;
    private List<Long> mercadorias;
    private List<Long> servicos;
    private double total;
}
