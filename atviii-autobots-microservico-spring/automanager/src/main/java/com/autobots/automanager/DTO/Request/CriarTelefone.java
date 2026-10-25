package com.autobots.automanager.DTO.Request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/** usuarioId ou empresaId (apenas um) é obrigatório no POST /telefones; é ignorado quando aninhado em usuário/empresa. */
@Getter
@Setter
public class CriarTelefone {
    @NotBlank(message = "ddd é obrigatorio")
    private String ddd;
    @NotBlank(message = "numero é obrigatorio")
    private String numero;
    private Long usuarioId;
    private Long empresaId;
}
