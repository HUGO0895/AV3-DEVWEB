package com.autobots.automanager.DTO.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarEmailDTO {
    @NotNull(message = "id precisa existir")
    private Long id;
    @Email(message = "endereco deve ser um e-mail válido")
    private String endereco;
}
