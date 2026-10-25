package com.autobots.automanager.DTO.Request;

import javax.validation.constraints.NotNull;

import com.autobots.automanager.enumeracoes.TipoVeiculo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarVeiculoDTO {
    @NotNull(message = "id precisa existir")
    private Long id;
    private TipoVeiculo tipo;
    private String modelo;
    private String placa;
    private Long proprietarioId;
}
