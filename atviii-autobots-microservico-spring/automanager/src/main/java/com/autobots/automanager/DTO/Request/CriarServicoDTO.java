package com.autobots.automanager.DTO.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarServicoDTO {
    @NotBlank(message = "nome é obrigatorio")
    private String nome;
    @NotNull(message = "valor é obrigatorio")
    @PositiveOrZero(message = "valor não pode ser negativo")
    private Double valor;
    private String descricao;
    @NotNull(message = "empresaId é obrigatorio")
    private Long empresaId;
}
