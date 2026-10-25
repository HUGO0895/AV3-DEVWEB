package com.autobots.automanager.DTO.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/** usuarioId é obrigatório no POST /emails; é ignorado quando aninhado em usuário. */
@Getter
@Setter
public class CriarEmailDTO {
    @NotBlank(message = "endereco é obrigatorio")
    @Email(message = "endereco deve ser um e-mail válido")
    private String endereco;
    private Long usuarioId;
}
