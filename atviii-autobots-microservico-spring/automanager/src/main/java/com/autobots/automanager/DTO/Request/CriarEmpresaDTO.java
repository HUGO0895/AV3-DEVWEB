package com.autobots.automanager.DTO.Request;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarEmpresaDTO {
    @NotBlank(message = "razaoSocial é obrigatoria")
    private String razaoSocial;
    private String nomeFantasia;
    private Date cadastro;
    @Valid
    private CriarEnderecoDTO endereco;
    @Valid
    private List<CriarTelefone> telefones;
}
