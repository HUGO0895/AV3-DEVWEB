package com.autobots.automanager.DTO.Request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarMercadoriaDTO {
    @NotBlank(message = "nome é obrigatorio")
    private String nome;
    @NotNull(message = "validade é obrigatoria")
    private Date validade;
    @NotNull(message = "fabricao é obrigatoria")
    private Date fabricao;
    private Date cadastro;
    @NotNull(message = "quantidade é obrigatoria")
    @PositiveOrZero(message = "quantidade não pode ser negativa")
    private Long quantidade;
    @NotNull(message = "valor é obrigatorio")
    @PositiveOrZero(message = "valor não pode ser negativo")
    private Double valor;
    private String descricao;
    @NotNull(message = "empresaId é obrigatorio")
    private Long empresaId;
    private Long fornecedorId;
}
