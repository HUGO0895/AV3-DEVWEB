package com.autobots.automanager.DTO.Request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarServicoDTO {
    @NotNull(message = "id precisa existir")
    private Long id;
    private String nome;
    @PositiveOrZero(message = "valor não pode ser negativo")
    private Double valor;
    private String descricao;
}
