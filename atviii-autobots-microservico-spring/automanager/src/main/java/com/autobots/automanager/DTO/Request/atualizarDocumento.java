package com.autobots.automanager.DTO.Request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.autobots.automanager.enumeracoes.TipoDocumento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class atualizarDocumento {
    @NotNull(message = "Id precisa existir")
    private Long id;
    private TipoDocumento tipo;
    private String numero;
    private Date dataEmissao;
}
