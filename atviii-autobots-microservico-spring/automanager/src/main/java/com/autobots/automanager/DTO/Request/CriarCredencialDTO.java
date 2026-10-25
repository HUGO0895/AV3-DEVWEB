package com.autobots.automanager.DTO.Request;

import javax.validation.constraints.NotNull;

import com.autobots.automanager.enumeracoes.TipoCredencial;

import lombok.Getter;
import lombok.Setter;

/** USUARIO_SENHA exige nomeUsuario e senha; CODIGO_BARRA exige codigo. */
@Getter
@Setter
public class CriarCredencialDTO {
    @NotNull(message = "usuarioId é obrigatorio")
    private Long usuarioId;
    @NotNull(message = "tipo é obrigatorio")
    private TipoCredencial tipo;
    private String nomeUsuario;
    private String senha;
    private Long codigo;
    private boolean inativo;
}
