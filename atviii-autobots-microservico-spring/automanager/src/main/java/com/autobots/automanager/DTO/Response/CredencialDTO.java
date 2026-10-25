package com.autobots.automanager.DTO.Response;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.TipoCredencial;

import lombok.Getter;
import lombok.Setter;

/** A senha nunca é devolvida. */
@Getter
@Setter
public class CredencialDTO extends RepresentationModel<CredencialDTO> {
    private Long id;
    private TipoCredencial tipo;
    private Date criacao;
    private Date ultimoAcesso;
    private boolean inativo;
    private String nomeUsuario;
    private Long codigo;
    private Long usuario;
}
