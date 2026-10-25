package com.autobots.automanager.DTO.Response;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.TipoDocumento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoDtO extends RepresentationModel<DocumentoDtO> {
    private Long id;
    private TipoDocumento tipo;
    private String numero;
    private Date dataEmissao;
    private Long usuario;
}
