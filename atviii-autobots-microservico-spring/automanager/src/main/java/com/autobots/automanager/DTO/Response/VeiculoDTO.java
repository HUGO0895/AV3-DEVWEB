package com.autobots.automanager.DTO.Response;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.TipoVeiculo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoDTO extends RepresentationModel<VeiculoDTO> {
    private Long id;
    private TipoVeiculo tipo;
    private String modelo;
    private String placa;
    private Long proprietario;
    private List<Long> vendas;
}
