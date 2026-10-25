package com.autobots.automanager.DTO.Response;

import java.util.List;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.PerfilUsuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {
    private Long id;
    private String nome;
    private String nomeSocial;
    private Set<PerfilUsuario> perfis;
    private Long empresa;
    private EnderecoDTO endereco;
    private List<TelefoneDto> telefones;
    private List<DocumentoDtO> documentos;
    private List<EmailDTO> emails;
    private List<CredencialDTO> credenciais;
    private List<Long> mercadorias;
    private List<Long> veiculos;
    private List<Long> vendas;
}
