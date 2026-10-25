package com.autobots.automanager.DTO.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.autobots.automanager.enumeracoes.TipoVeiculo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarVeiculoDTO {
    @NotNull(message = "tipo é obrigatorio")
    private TipoVeiculo tipo;
    @NotBlank(message = "modelo é obrigatorio")
    private String modelo;
    @NotBlank(message = "placa é obrigatoria")
    private String placa;
    @NotNull(message = "proprietarioId é obrigatorio")
    private Long proprietarioId;
}
