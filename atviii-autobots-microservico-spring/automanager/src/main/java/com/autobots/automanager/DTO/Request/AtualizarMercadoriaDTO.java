package com.autobots.automanager.DTO.Request;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarMercadoriaDTO {
    @NotNull(message = "id precisa existir")
    private Long id;
    private String nome;
    private Date validade;
    private Date fabricao;
    @PositiveOrZero(message = "quantidade não pode ser negativa")
    private Long quantidade;
    @PositiveOrZero(message = "valor não pode ser negativo")
    private Double valor;
    private String descricao;
    private Long fornecedorId;
}
