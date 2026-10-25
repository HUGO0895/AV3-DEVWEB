package com.autobots.automanager.DTO.Request;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.autobots.automanager.enumeracoes.PerfilUsuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarUsuarioDTO {
    @NotNull(message = "ID é necessario")
    private Long id;
    private String nome;
    private String nomeSocial;
    private Set<PerfilUsuario> perfis;
    private Long empresaId;
}
