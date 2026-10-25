package com.autobots.automanager.DTO.Request;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarVendaDTO {
    @NotBlank(message = "identificacao é obrigatoria")
    private String identificacao;
    private Date cadastro;
    @NotNull(message = "empresaId é obrigatorio")
    private Long empresaId;
    @NotNull(message = "clienteId é obrigatorio")
    private Long clienteId;
    @NotNull(message = "funcionarioId é obrigatorio")
    private Long funcionarioId;
    private Long veiculoId;
    private Set<Long> mercadoriaIds;
    private Set<Long> servicoIds;
}
