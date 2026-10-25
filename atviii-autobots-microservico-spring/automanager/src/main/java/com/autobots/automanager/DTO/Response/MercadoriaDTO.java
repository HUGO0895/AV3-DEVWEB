package com.autobots.automanager.DTO.Response;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MercadoriaDTO extends RepresentationModel<MercadoriaDTO> {
    private Long id;
    private String nome;
    private Date validade;
    private Date fabricao;
    private Date cadastro;
    private long quantidade;
    private double valor;
    private String descricao;
    private Long empresa;
    private Long fornecedor;
}
