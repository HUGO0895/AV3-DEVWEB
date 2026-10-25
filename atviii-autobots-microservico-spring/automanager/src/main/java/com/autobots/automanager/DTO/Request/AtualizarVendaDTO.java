package com.autobots.automanager.DTO.Request;

import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/** Campos nulos não são alterados. Se mercadoriaIds/servicoIds forem informados, substituem a lista atual. */
@Getter
@Setter
public class AtualizarVendaDTO {
    @NotNull(message = "id precisa existir")
    private Long id;
    private String identificacao;
    private Long clienteId;
    private Long funcionarioId;
    private Long veiculoId;
    private Set<Long> mercadoriaIds;
    private Set<Long> servicoIds;
}
