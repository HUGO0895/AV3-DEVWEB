package com.autobots.automanager.DTO.Request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.autobots.automanager.enumeracoes.TipoDocumento;

import lombok.Getter;
import lombok.Setter;

/** usuarioId é obrigatório no POST /documentos; é ignorado quando aninhado em usuário. */
@Getter
@Setter
public class CriarDocumento {
    @NotNull(message = "tipo precisa existir")
    private TipoDocumento tipo;
    @NotBlank(message = "numero precisa existir")
    private String numero;
    @NotNull(message = "dataEmissao precisa existir")
    private Date dataEmissao;
    private Long usuarioId;
}
