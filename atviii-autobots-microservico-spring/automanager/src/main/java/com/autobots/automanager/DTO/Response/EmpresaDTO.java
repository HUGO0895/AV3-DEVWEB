package com.autobots.automanager.DTO.Response;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDTO extends RepresentationModel<EmpresaDTO> {
    private Long id;
    private String razaoSocial;
    private String nomeFantasia;
    private Date cadastro;
    private EnderecoDTO endereco;
    private List<TelefoneDto> telefones;
    private List<Long> usuarios;
    private List<Long> mercadorias;
    private List<Long> servicos;
    private List<Long> vendas;
}
