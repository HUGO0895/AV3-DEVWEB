package com.autobots.automanager.DTO.Request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarEmpresaDTO {
    @NotNull(message = "id precisa existir")
    private Long id;
    private String razaoSocial;
    private String nomeFantasia;
}
