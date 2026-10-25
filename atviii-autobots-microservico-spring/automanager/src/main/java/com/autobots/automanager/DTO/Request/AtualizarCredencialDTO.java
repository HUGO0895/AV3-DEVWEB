package com.autobots.automanager.DTO.Request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarCredencialDTO {
    @NotNull(message = "id precisa existir")
    private Long id;
    private String nomeUsuario;
    private String senha;
    private Long codigo;
    private Boolean inativo;
}
